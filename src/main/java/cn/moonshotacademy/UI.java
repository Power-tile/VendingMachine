package cn.moonshotacademy;

import cn.moonshotacademy.Controller; // TODO: BEAN SINGLETON INIT
import cn.moonshotacademy.exceptions.*;
import cn.moonshotacademy.interfaces.*;

import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;

public class UI implements UITemplate {
    private int status;
    private Scanner scanner;

    private UserTemplate currentUser;
    private Integer remainingTry = 3;
    private Integer storageIndex;
    private String templateChoice;
    private Integer templateCount;

    private boolean login;
    private boolean pwdChange;
    private String newPassword;

    private Controller controller;

    public UI(Integer storageIndex, Controller controller) {
        scanner = new Scanner(System.in);
        this.storageIndex = storageIndex;
        this.controller = controller;
        logout(0);
    }

    public Integer getStatus() {
        return Integer.valueOf(status);
    }
    public UserTemplate getCurrentUser() {
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

    public ArrayList<String> outputMenu() {
        HashMap<String, Integer> info = this.controller.getStorageMenu(storageIndex);

        System.out.printf("-1: Logout\n");
        System.out.printf("0: Change Password, $0; Product Remaining: --\n");

        int num = 0;
        ArrayList<String> seq = new ArrayList<String>();
        for (String i : info.keySet()) {
            System.out.printf("%d: %s, $%d; Product Remaining: %d\n", ++num,
                                                                      i,
                                                                      this.controller.getProductCost(storageIndex, i, 0),
                                                                      this.controller.getTemplateCount(storageIndex, i));
            seq.add(i);
        }
        return seq;
    }

    public void interact() {
        while (true) {
            try {
                process();
            } catch (UserNotExistException | InvalidInputException | PasswordIncorrectException
                    | NewPasswordIncorrectException | NotEnoughMoneyException | NotEnoughStorageException e) {
                System.out.println(e.getMessage());
            } catch (PasswordTrialZeroedException e) {
                System.out.println(e.getMessage());
                if (status == 2) {
                    logout(1);
                } else {
                    logout(2);
                }
            }
        }
    }

    public void process() throws BaseException {
        switch (status) {
            case 0: { // Initialize
                System.out.println("Welcome to Daniel's Vending Machine!");
                logout(0); // logout with hello
                break;
            }
            case 1: { // Input Username
                System.out.print("Please enter your username: ");
                String username = scanner.nextLine();
                currentUser = this.controller.checkUserName(username);
                if (currentUser == null) {
                    throw new UserNotExistException("User " + username + " does not exist! Please re-enter your username.");
                }
                break;
            }
            case 2: { // Input Password
                System.out.print("Please enter your password: ");
                String pwd = scanner.nextLine();
                if (remainingTry.intValue() == 0) {
                    throw new PasswordTrialZeroedException("Sorry, you are out of your password chance. Please re-enter your username.");
                } else if (!this.controller.checkUserPassword(currentUser, pwd)) {
                    remainingTry--;
                    throw new PasswordIncorrectException("Password incorrect! Please re-enter your password. You have " + remainingTry + " chances left.");
                } else {
                    login = true;
                    remainingTry = 3;
                    System.out.printf("Welcome, %s! Your remaining balance: $%d\n", currentUser.getName(), currentUser.getBalance());
                }
                break;
            }
            case 3: { // Input template
                ArrayList<String> seq = outputMenu();

                System.out.printf("Please enter the index of the intended product: ");
                if (scanner.hasNextInt()) {
                    Integer templateIndex = Integer.valueOf(scanner.nextInt());
                    scanner.nextLine();
                    if (templateIndex.intValue() > 0 && templateIndex.intValue() <= seq.size()) { // Not changing password
                        templateChoice = seq.get(templateIndex - 1);

                        System.out.printf("You choosed the product %s. Remaining Products: %d, Cost: $%d\n",
                                          templateChoice,
                                          this.controller.getTemplateCount(storageIndex, templateChoice),
                                          this.controller.getProductCost(storageIndex, templateChoice, 0));
                    } else if (templateIndex.intValue() == 0) { // Changing password
                        pwdChange = true;
                    } else if (templateIndex.intValue() == -1) {
                        System.out.printf("See you next time, %s!\n", currentUser.getName());
                        logout(0);
                        return; // Logout with hello
                    } else {
                        throw new InvalidInputException("Your input isn't a valid integer! Please re-enter your choice."); // invalid integer input
                    }
                } else {
                    scanner.nextLine();
                    throw new InvalidInputException("Your input isn't a valid integer! Please re-enter your choice."); // invalid integer input
                }
                break;
            }
            case 4: { // Input count / pwd
                if (pwdChange) { // change password
                    System.out.print("Please re-enter your current password: ");
                    String pwd = scanner.nextLine();
                    if (remainingTry == 0) {
                        throw new PasswordTrialZeroedException("Sorry, you are out of your changing password chance. Please re-select a product."); // pwd trial zeroed for products
                    } else if (!currentUser.checkPassword(pwd)) {
                        remainingTry--;
                        throw new PasswordIncorrectException("Password incorrect! Please re-enter your password. You have " + remainingTry + " chances left.");
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
                            return; // Init
                        } else if (templateCount == -1) {
                            System.out.printf("See you next time, %s!\n", currentUser.getName());
                            logout(0);
                            return; // Init
                        } else if (templateCount > 0 && !this.controller.checkSize(storageIndex, templateChoice, templateCount)) {
                            throw new NotEnoughStorageException("Not enough product! We only have "
                                                              + this.controller.getTemplateCount(storageIndex, templateChoice)
                                                              + " products. Please select a smaller amount.");
                        } else if (templateCount < -1) {
                            throw new InvalidInputException("Your input isn't a valid integer! Please re-enter your choice."); // invalid integer input
                        } else if (this.controller.calculateCost(storageIndex, templateChoice, templateCount)> currentUser.getBalance().intValue()) {
                            throw new NotEnoughMoneyException("Sorry, you don't have enough money. Please choose another product, or re-enter the count of the product.");
                        }

                        // output result
                        System.out.printf("You are trying to by %d %s, which will cost you $%d.\n",
                                            templateCount, templateChoice, this.controller.calculateCost(storageIndex, templateChoice, templateCount));
                    } else {
                        scanner.nextLine();
                        throw new InvalidInputException("Your input isn't a valid integer! Please re-enter your choice."); // invalid integer input
                    }
                }
                break;
            }
            case 5: { // Confirm / New Password
                if (pwdChange) { // confirm changed password
                    System.out.print("Please confirm new password: ");
                    String confirmPassword = scanner.nextLine();
                    if (remainingTry == 0) {
                        throw new PasswordTrialZeroedException("Sorry, you are out of your changing password chance. Please re-select a product.");
                    } else if (newPassword.equals(confirmPassword)) {
                        currentUser.setPassword(newPassword);
                        System.out.print("Password Changed!\n");
                        remainingTry = 3;
                    } else {
                        remainingTry--;
                        throw new PasswordIncorrectException("Password incorrect! Please re-enter your password. You have " + remainingTry + " chances left.");
                    }
                } else {
                    System.out.print("Please confirm your order: (Y/N): ");
                    String confirmState = scanner.nextLine();
                    if (confirmState.length() == 1) { // input size == 1
                        if (confirmState.startsWith("Y") || confirmState.startsWith("y")) { // confirm payment
                            this.controller.request(currentUser, storageIndex, templateChoice, templateCount);
                            System.out.printf("Purchase complete! Please take away your product(s). Remaining balance: %d\n", currentUser.getBalance());
                        } else if (confirmState.startsWith("N") || confirmState.startsWith("n")) { // abandon payment
                            System.out.println("Abandoning payment ...");
                            logout(2);
                            return; // Init
                        } else { // input incorrect
                            throw new InvalidInputException("Unable to understand your choice! Did you input Y or N?");
                        }
                    } else { // input incorrect
                        throw new InvalidInputException("Unable to understand your choice! Did you input Y or N?");
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
                        return; // Init
                    } else if (repeatState.startsWith("N") || repeatState.startsWith("n")) { // abandon payment
                        System.out.printf("Thank you! See you next time, %s!\n", currentUser.getName());
                        logout(0);
                        return; // Logout with hello
                    } else { // input incorrect
                        throw new InvalidInputException("Unable to understand your choice! Did you input Y or N?");
                    }
                } else {
                    throw new InvalidInputException("Unable to understand your choice! Did you input Y or N?");
                }
            }
        }
        status++;
    }
}