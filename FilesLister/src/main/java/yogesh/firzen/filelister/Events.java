package yogesh.firzen.filelister;

/**
 * Created by vladimir on 27/07/18.
 */
public class Events {

    public static class Selected {

        private String message;

        public Selected(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}