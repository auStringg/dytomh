import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IPixelFormat;
import com.xuggle.xuggler.IVideoPicture;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VideoCapture {

    private static final double FRAME_RATE = 25;
    private static final int SCREEN_WIDTH = 1920;
    private static final int SCREEN_HEIGHT = 1080;
    private static final int GOP_LENGTH_IN_FRAMES = 25;

    private static final String SERVER_URL = "http://your-server-url.com";
    private static final String VIDEO_PATH = "/path/to/save/videos/";

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

    public static void main(String[] args) throws Exception {

        // create a screen shot every 0.5 seconds
        long captureInterval = 500;

        // create a writer to write the video
        IMediaWriter writer = ToolFactory.makeWriter(VIDEO_PATH + DATE_FORMAT.format(new Date()) + ".mp4");

        // set the video properties
        writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_H264, SCREEN_WIDTH, SCREEN_HEIGHT);

        // set the GOP length
        writer.getContainer().getStream(0).getStreamCoder().setGopSize(GOP_LENGTH_IN_FRAMES);

        // create a robot to capture the screen
        Robot robot = new Robot();

        // set the capture area
        Rectangle captureArea = new Rectangle(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        // set the initial timestamp
        long timestamp = 0;

        // loop indefinitely
        while (true) {

            // capture the screen
            BufferedImage screen = robot.createScreenCapture(captureArea);

            // create a video picture from the screen
            IVideoPicture frame = ToolFactory.makeVideoPicture(screen, IPixelFormat.Type.YUV420P);

            // set the frame timestamp
            frame.setPts(timestamp);

            // encode the frame
            writer.encodeVideo(0, frame);

            // save the screenshot as a JPEG file
            String filename = DATE_FORMAT.format(new Date()) + ".jpg";
            ImageIO.write(screen, "jpg", new File(filename));

            // send the screenshot to the server
            sendToServer(filename);

            // increment the timestamp
            timestamp += (1000 / FRAME_RATE);

            // wait for the next capture interval
            Thread.sleep(captureInterval);
        }

        // close the writer
        writer.close();
    }

    private static void sendToServer(String filename) throws Exception {
        String boundary = Long.toHexString(System.currentTimeMillis()); // random boundary string
        URL url = new URL(SERVER_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        OutputStream output = connection.getOutputStream();
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8), true);

        File file = new File(filename);
        writer.println("--" + boundary);
        writer.println("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"");
        writer.println("Content-Type: image/jpeg");
        writer.println();

        FileInputStream input = new FileInputStream(file);
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
        input.close();

        writer.println();
        writer.println("--" + boundary + "--");
        writer.flush();

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            // do something with the response
        }
        reader.close();

        connection.disconnect();
    }

}
