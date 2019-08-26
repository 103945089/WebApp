package tmproject.hlhj.fhp.webapp;

public class A {

    /**
     * code : 1
     * msg : 操作成功
     * data : {"back":"http://app.schlhjnetworktesturl.com/upload/admin/20190520/5d08ba074f6828210fe914972e227faa.png","url":"https://www.baidu.com"}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * back : http://app.schlhjnetworktesturl.com/upload/admin/20190520/5d08ba074f6828210fe914972e227faa.png
         * url : https://www.baidu.com
         */

        private String back;
        private String url;

        public String getBack() {
            return back;
        }

        public void setBack(String back) {
            this.back = back;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
