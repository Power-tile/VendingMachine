package cn.moonshotacademy;

import cn.moonshotacademy.Controller;

import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;

public class UI {
    private int status;
    private Scanner scanner;

    private User currentUser;
    private Integer remainingTry = 3;
    private Integer storageIndex;
    private ProductTemplate templateChoice;
    private Integer templateCount;

    private boolean login;
    private boolean pwdChange;
    private String newPassword;

    public UI(Integer _storageIndex) {
        scanner = new Scanner(System.in);
        storageIndex = _storageIndex;
        logout(0);
    }

    public Integer getStatus() {
        return Integer.valueOf(status);
    }
    public User getCurrentUser() {
        return currentUser;
    }

    public void logout(Integer mode) { // O: Logout, 2: Init, 1: Logout without hello
        templateChoice = null;
        templateCount = 0;
        pwdChange = false;
        newPassword = null;
        remainingTry = 3;

        if (mode.intValue() == 0 || mode.intValue() == 1) {
            status = mode;
            currentUser = null;
            login = false;
        } else {
            status = 3;
        }
    }

    public ArrayList<ProductTemplate> outputMenu() {
        HashMap<ProductTemplate, Integer> info = Controller.getInstance().getStorageMenu(storageIndex);

        System.out.printf("-1: Logout\n");
        System.out.printf("0: Change Password, $0; Product Remaining: --\n");

        ArrayList<ProductTemplate> seq = new ArrayList<ProductTemplate>();
        int num = 0;
        for (ProductTemplate i : info.keySet()) {
            System.out.printf("%d: %s, $%d; Product Remaining: %d\n", ++num, i.getName(), i.getCost(), Controller.getInstance().getTemplateCount(storageIndex, i));
            seq.add(i);
        }

        return seq;
    }

    public void interact() {
        while (true) {
            switch (status) {
                case 0: { // Initialize
                    System.out.println("Welcome to Daniel's Vending Machine!");
                    logout(0);
                    break;
                }
                case 1: { // Input Username
                    System.out.print("Please enter your username: ");
                    String username = scanner.nextLine();
                    currentUser = Controller.getInstance().checkUserName(username);
                    if (currentUser == null) {
                        warning(Integer.valueOf(1), username); // user not exist
                        continue;
                    }
                    break;
                }
                case 2: { // Input Password
                    System.out.print("Please enter your password: ");
                    String pwd = scanner.nextLine();
                    if (remainingTry.intValue() == 0) {
                        warning(Integer.valueOf(8), null); // out of pwd chance
                        logout(1);
                        continue;
                    } else if (!Controller.getInstance().checkUserPassword(currentUser, pwd)) {
                        warning(Integer.valueOf(2), Integer.toString(remainingTry)); // pwd incorrect
                        remainingTry--;
                        continue;
                    } else {
                        login = true;
                        remainingTry = 3;
                        System.out.printf("Welcome, %s! Your remaining balance: $%d\n", currentUser.getName(), currentUser.getBalance());
                    }
                    break;
                }
                case 3: { // Input template
                    ArrayList<ProductTemplate> seq = outputMenu();

                    System.out.printf("Please enter the index of the intended product: ");
                    if (scanner.hasNextInt()) {
                        Integer templateIndex = Integer.valueOf(scanner.nextInt());
                        scanner.nextLine();
                        if (templateIndex.intValue() > 0 && templateIndex.intValue() <= seq.size()) { // Not changing password
                            templateChoice = seq.get(templateIndex - 1);

                            System.out.printf("You choosed the product %s. Remaining Products: %d, Cost: $%d\n",
                            templateChoice.getName(),
                            Controller.getInstance().getTemplateCount(storageIndex, templateChoice),
                            templateChoice.getCost());
                        } else if (templateIndex.intValue() == 0) { // Changing password
                            pwdChange = true;
                        } else if (templateIndex.intValue() == -1) {
                            System.out.printf("See you next time, %s!\n", currentUser.getName());
                            logout(0);
                            continue;
                        } else {
                            warning(Integer.valueOf(3), null); // invalid integer input
                            continue;
                        }
                    } else {
                        scanner.nextLine();
                        warning(Integer.valueOf(3), null); // invalid integer input
                        continue;
                    }
                    
                    break;
                }
                case 4: { // Input count / pwd
                    if (pwdChange) { // change password
                        System.out.print("Please re-enter your current password: ");
                        String pwd = scanner.nextLine();
                        if (remainingTry == 0) {
                            warning(Integer.valueOf(9), null);
                            logout(2);
                            continue;
                        } else if (!currentUser.checkPassword(pwd)) {
                            warning(Integer.valueOf(2), Integer.toString(remainingTry)); // pwd incorrect
                            remainingTry--;
                            continue;
                        }

                        remainingTry = 3;
                        System.out.print("Please enter your new password: ");
                        newPassword = scanner.nextLine();
                    } else {
                        System.out.printf("Please enter your desired count (enter 0 to re-choose your product, enter -1 to logout): ");
                        if (scanner.hasNextInt()) {
                            templateCount = scanner.nextInt();
                            scanner.nextLine();
                            if (templateCount == 0) {
                                System.out.println("Please re-choose your product: ");
                                logout(2);
                                continue;
                            } else if (templateCount == -1) {
                                System.out.printf("See you next time, %s!\n", currentUser.getName());
                                logout(0);
                                continue;
                            } else if (templateCount > 0 && !Controller.getInstance().checkSize(storageIndex, templateChoice, templateCount)) {
                                warning(Integer.valueOf(4), Integer.toString(Controller.getInstance().getTemplateCount(storageIndex, templateChoice))); // not enough storage
                                continue;
                            } else if (templateCount < -1) {
                                warning(Integer.valueOf(3), null); // invalid input
                                continue;
                            } else if (templateChoice.getCost() * templateCount > currentUser.getBalance().intValue()) {
                                warning(Integer.valueOf(7), null); // not enough money
                                continue;
                            }

                            // output result
                            System.out.printf("You are trying to by %d %s, which will cost you $%d.\n",
                                              templateCount, templateChoice.getName(), templateChoice.getCost() * templateCount);
                        } else {
                            scanner.nextLine();
                            warning(Integer.valueOf(3), null); // invalid integer
                            continue;
                        }
                    }
                    break;
                }
                case 5: { // Confirm / New Password
                    if (pwdChange) { // confirm changed password
                        System.out.print("Please confirm new password: ");
                        String confirmPassword = scanner.nextLine();
                        if (remainingTry == 0) {
                            warning(Integer.valueOf(9), null);
                            logout(2);
                            continue;
                        } else if (newPassword.equals(confirmPassword)) {
                            currentUser.changePassword(newPassword);
                            System.out.print("Password Changed!\n");
                            remainingTry = 3;
                        } else {
                            warning(Integer.valueOf(5), Integer.toString(remainingTry));
                            remainingTry--;
                            continue;
                        }
                    } else {
                        System.out.print("Please confirm your order: (Y/N): ");
                        String confirmState = scanner.nextLine();
                        if (confirmState.length() == 1) { // input size == 1
                            if (confirmState.startsWith("Y") || confirmState.startsWith("y")) { // confirm payment
                                Controller.getInstance().request(currentUser, storageIndex, templateChoice, templateCount);
                                System.out.printf("Purchase complete! Please take away your product(s). Remaining balance: %d\n", currentUser.getBalance());
                            } else if (confirmState.startsWith("N") || confirmState.startsWith("n")) { // abandon payment
                                System.out.println("Abandoning payment ...");
                                logout(2);
                                continue;
                            } else { // input incorrect
                                warning(Integer.valueOf(6), null);
                                continue;
                            }
                        } else {
                            warning(Integer.valueOf(6), null);
                            continue;
                        }
                    }      
                    break;
                }
                case 6: { // Thanks & Repeating
                    System.out.print("Continue buying? Y/N: ");
                    String repeatState = scanner.nextLine();
                    if (repeatState.length() == 1) { // input size == 1
                        if (repeatState.startsWith("Y") || repeatState.startsWith("y")) { // confirm payment
                            System.out.printf("Happy vending! Your remaining balance: $%d\n", currentUser.getBalance());
                            logout(2);
                            continue;
                        } else if (repeatState.startsWith("N") || repeatState.startsWith("n")) { // abandon payment
                            System.out.printf("Thank you! See you next time, %s!\n", currentUser.getName());
                            logout(0);
                            continue;
                        } else { // input incorrect
                            warning(Integer.valueOf(6), null);
                            continue;
                        }
                    } else {
                        warning(Integer.valueOf(6), null);
                        continue;
                    }
                }
            }
            status++;
        }
    }

    public void warning(Integer warningIndex, String param) {
        switch (warningIndex.intValue()) {
            case 1: { // user not exist
                System.out.printf("User %s does not exist! Please re-enter your username.\n", param);
                break;
            }
            case 2: { // pwd incorrect
                System.out.printf("Password incorrect! Please re-enter your password. You have %s chances left.\n", param);
                break;
            }
            case 3: { // invalid integer input
                System.out.printf("Your input isn't a valid integer! Please re-enter your choice.\n");
                break;
            }
            case 4: { // not enough storage
                System.out.printf("Not enough product! We only have %s products. Please select a smaller amount.", param);
                break;
            }
            case 5: { // new pwd incorrect
                System.out.printf("These two passwords aren't identical! Please re-enter your new password. You have %s chances left.\n", param);
                break;
            }
            case 6: { // not y or n
                System.out.println("Unable to understand your choice! Did you input Y or N?");
                break;
            }
            case 7: { // not enough money
                System.out.println("Sorry, you don't have enough money. Please choose another product, or re-enter the count of the product.");
                break;
            }
            case 8: { // no pwd chance
                System.out.println("Sorry, you are out of your password chance. Please re-enter your username.");
                break;
            }
            case 9: { // no pwd chance for product
                System.out.println("Sorry, you are out of your changing password chance. Please re-select a product.");
                break;
            }
        }
    }
}