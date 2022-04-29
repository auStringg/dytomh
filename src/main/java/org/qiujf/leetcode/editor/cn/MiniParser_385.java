//迷你语法分析器
package org.qiujf.leetcode.editor.cn;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



//todo 失败 会有多余的[]符号，后期在改 2022年4月18日
public class MiniParser_385 {
    public static void main(String[] args) {
        Solution solution = new MiniParser_385().new Solution();
        String s = "456,[789]";
        solution.deserialize(s);
    }
    //leetcode submit region begin(Prohibit modification and deletion)

    /**
     * // 这是允许创建嵌套列表的接口。
     * // 你不应该实现它，或者推测它的实现
     * public Interface NestedInteger {
     * // 构造函数初始化一个空的嵌套列表。
     * 公共嵌套整数（）；
     * <p>
     * // 构造函数初始化一个整数。
     * public NestedInteger(int value);
     * <p>
     * // @return 如果此 NestedInteger 包含单个整数而不是嵌套列表，则返回 true。
     * public boolean isInteger();
     * <p>
     * // @return 这个 NestedInteger 持有的单一整数，如果它持有一个整数
     * // 如果此 NestedInteger 包含嵌套列表，则返回 null
     * 公共整数 getInteger();
     * <p>
     * // 将此 NestedInteger 设置为保存单个整数。
     * public void setInteger(int value);
     * <p>
     * // 将此 NestedInteger 设置为保存一个嵌套列表并向其中添加一个嵌套整数。
     * public void add(NestedInteger ni);
     * <p>
     * // @return 这个 NestedInteger 拥有的嵌套列表，如果它拥有一个嵌套列表
     * // 如果此 NestedInteger 包含单个整数，则返回空列表
     * public List<NestedInteger> getList();
     * }
     */
    class Solution {
        public  boolean isBlank(CharSequence cs) {
            int strLen;
            if (cs != null && (strLen = cs.length()) != 0) {
                for(int i = 0; i < strLen; ++i) {
                    if (!Character.isWhitespace(cs.charAt(i))) {
                        return false;
                    }
                }

                return true;
            } else {
                return true;
            }
        }

        public NestedInteger deserialize(String s) {
            NestedInteger nestedInteger = deserialize2(s);
            return nestedInteger;
        }
        public NestedInteger deserialize2(String s) {
            /**
             * 双指针，前后扫描 start, end;
             * 1.如果碰到【，后面的指针从后往前扫描直到找到】,这个时候会出现 xxx，xxx, [xxx,xxx],xxx,xxx 这种情况
             * index从0 到start切割，start+1 到 end -1切割 ，end 到length()切割.
             * 把前面的数字添加到ret,然后用deserialize(String)递归解析，end到length()的添加到ret;
             * 2.如果没有【  直接解析，添加到ret里
             */
            NestedInteger ret = null;
            if(!s.contains("[")){
                String[] split = s.split(",");
                if(split.length >1) {
                    ret = new NestedInteger();
                    for (String s1 : split) {
                        NestedInteger nestedInteger = new NestedInteger(Integer.parseInt(s1));
                        ret.add(nestedInteger);
                    }
                }else{
                    ret = new NestedInteger(Integer.parseInt(s));
                }
                return ret;
            }else{
                int start = 0,end = s.length() -1;

                while(start <end){
                    if('[' ==s.charAt(start)){
                        while(start <end){
                            if(']' == s.charAt(end)){
                                ret = new NestedInteger();
                                //0 ~ start部分
                                String startS = s.substring(0,start);
                                if(!isBlank(startS)) {
                                    String[] split = startS.split(",");

                                    for (String s1 : split) {
                                        NestedInteger nestedInteger = new NestedInteger(Integer.parseInt(s1));
                                        ret.add(nestedInteger);
                                    }

                                }
                                //start ~end部分
                                NestedInteger tmp = new NestedInteger();
                                tmp.add(deserialize2(s.substring(start,end)));
                                ret.add(tmp);

                                //end ~ length()部分
                                String endS = s.substring(end+1);
                                if(!isBlank(endS)) {
                                    String[] strings = endS.split(",");
                                    for (String s1 : strings) {
                                        NestedInteger nestedInteger = new NestedInteger(Integer.parseInt(s1));
                                        ret.add(nestedInteger);
                                    }
                                }
                                break;
                            }
                            end --;
                        }
                        break;
                    }
                    start ++;
                }

                if(start == 0) {
                    return ret.getList().get(0);
                }
                return ret;

            }


        }
    }

//leetcode submit region end(Prohibit modification and deletion)

    public class NestedInteger {
        // 构造函数初始化一个空的嵌套列表。
        public NestedInteger() {

        }

        // 构造函数初始化一个整数。
        public NestedInteger(int value) {

        }

        // @return 如果此 NestedInteger 包含单个整数而不是嵌套列表，则返回 true。
        public boolean isInteger() {
            return true;
        }

        // @return 这个 NestedInteger 持有的单一整数，如果它持有一个整数
        // 如果此 NestedInteger 包含嵌套列表，则返回 null
        public Integer getInteger() {
            return 1;
        }

        // 将此 NestedInteger 设置为保存单个整数。
        public void setInteger(int value) {

        }

        // 将此 NestedInteger 设置为保存一个嵌套列表并向其中添加一个嵌套整数。
        public void add(NestedInteger ni) {

        }

        // @return 这个 NestedInteger 拥有的嵌套列表，如果它拥有一个嵌套列表
        // 如果此 NestedInteger 包含单个整数，则返回空列表
        public List<NestedInteger> getList() {

            return null;
        }
    }


}