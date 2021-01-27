import controller.Controller;
import view.Menu;

public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();
        new Controller(menu);
    }
}
