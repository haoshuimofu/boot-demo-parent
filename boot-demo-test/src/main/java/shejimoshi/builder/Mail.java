package shejimoshi.builder;

/**
 * @Author ddmc
 * @Create 2019-03-11 14:08
 */
public class Mail {

    private String subject;
    private String content;
    private String sender;
    private String receivers;
    private String copyTo;

    @Override
    public String toString() {
        return "subject=" + subject + "; content=" + content + "; sender=" + sender + "; receivers=" + receivers + "; copyTo=" + copyTo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceivers() {
        return receivers;
    }

    public void setReceivers(String receivers) {
        this.receivers = receivers;
    }

    public String getCopyTo() {
        return copyTo;
    }

    public void setCopyTo(String copyTo) {
        this.copyTo = copyTo;
    }

    static class MailBuilder{
        private Mail mail;

        public MailBuilder() {
            this.mail = new Mail();
        }

        public MailBuilder subject(String subject) {
            mail.setSubject(subject);
            return this;
        }

        public MailBuilder content(String content) {
            mail.setContent(content);
            return this;
        }

        public MailBuilder sender(String sender) {
            mail.setSender(sender);
            return this;
        }

        public MailBuilder receivers(String receivers) {
            mail.setReceivers(receivers);
            return this;
        }

        public MailBuilder copyTo(String copyTo) {
            mail.setCopyTo(copyTo);
            return this;
        }

        public Mail builder() {
            return mail;
        }

    }
}