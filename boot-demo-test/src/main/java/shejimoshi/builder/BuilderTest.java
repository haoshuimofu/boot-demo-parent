package shejimoshi.builder;

/**
 * @Author ddmc
 * @Create 2019-03-11 15:45
 */
public class BuilderTest {

    public static void main(String[] args) {
        Mail mail = new Mail.MailBuilder()
                .subject("主题")
                .content("内容")
                .sender("发送人")
                .receivers("接收人")
                .copyTo("抄送给").builder();
        System.out.println(mail.toString());

    }
}