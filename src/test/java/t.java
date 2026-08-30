public class t {


    public static void main(String[] args) {
        System.out.println(GenerateCode());
        System.out.println(GenerateAdminID());
    }


    public static String GenerateCode(){
        String zf = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        String code = "";
        for (int i = 0; i < 10; i++) {
            int index = (int) (Math.random() * zf.length());
            code += zf.charAt(index);
        }
        return code;
    }


    public static String GenerateAdminID(){
        //长度为15
        String zf = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        String adminID = "";
        for (int i = 0; i < 15; i++) {
            int index = (int) (Math.random() * zf.length());
            adminID += zf.charAt(index);
        }
        return adminID;
    }

}
