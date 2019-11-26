package cn.moonshotacademy;

import cn.moonshotacademy.products.*;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        Controller controller = ac.getBean("controller", Controller.class);

        Storage first = new Storage();
        controller.addStorage(first);
        for (int i = 0; i < 10000; i++) {
            controller.addItemToStorage(1, (Product) (ac.getBean("hoh", HOH.class)));
        }
        for (int i = 0; i < 100; i++) {
            controller.addItemToStorage(1, (Product) (ac.getBean("hclo", HClO.class)));
        }
        
        controller.addUser(ac.getBean("user1", User.class));
        controller.addUser(ac.getBean("user2", User.class));
        controller.addUI(Integer.valueOf(1));

        controller.getUI(1).interact();
    }
}
