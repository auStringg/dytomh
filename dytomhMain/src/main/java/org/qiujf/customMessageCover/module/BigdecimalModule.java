package org.qiujf.customMessageCover.module;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.jaxrs.json.PackageVersion;

public class BigdecimalModule extends Module {
    @Override
    public String getModuleName() {
        return "BigdecimalModule";
    }

    @Override
    public Version version() {
        return PackageVersion.VERSION;
    }

    @Override
    public void setupModule(SetupContext context) {
        context.addSerializers(new BigdecimalSerializers());
    }
}
