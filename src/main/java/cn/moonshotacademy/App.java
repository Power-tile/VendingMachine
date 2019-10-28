package cn.moonshotacademy;

public class App {
    public static void main(String[] args) {
        Controller controller = Controller.getInstance();
        controller.getUI(1).interact();
    }
}
