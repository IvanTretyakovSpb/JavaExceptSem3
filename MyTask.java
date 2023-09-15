package seminar_3.homework;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Scanner;

public class MyTask {
    public static void main(String[] args) {
        boolean isContinue = true;

        while (isContinue) {
            String data = getDataFromUser();
            int errorCode = checkData(data);
            printErrorCode(errorCode);

            // если введено установленное количество данных, то парсим данные и записываем в файл
            if (errorCode == 0) {
                try {
                    User user = parseData(data);
                    writeFile(user);
                } catch (NumberFormatException | ParseException e) {
                    System.out.println(e.getMessage());
                } catch (GenderFormatException e) {
                    System.out.println(e.getMessage());
                    System.out.println(Arrays.toString(e.getStackTrace()));
                }
            }

            if (!askForContinue().equalsIgnoreCase("YES")) {
                isContinue = false;
            }
        }
    }

    // метод для запроса исходных данных у пользователя
    public static String getDataFromUser() {
        System.out.println("""
                Введите следующие данные, разделенные пробелом:
                Фамилия, Имя, Отчество, дата рождения, номер телефона, пол.
                                
                Форматы данных (порядок ввода произвольный):
                фамилия, имя, отчество - строки,
                дата рождения - строка формата dd.mm.yyyy,
                номер телефона - целое беззнаковое число без форматирования,
                пол - символ латиницей f или m.
                _____________________________________________________________""");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    // метод для количественной проверки введенных данных и получения кода ошибки
    public static int checkData(String data) {
        final int DATA_COUNT = 6; // верное количество единиц данных для ввода
        if (data.isBlank()) {
            return 1;
        }
        String[] dataParts = data.split(" ");
        if (dataParts.length == DATA_COUNT) {
            return 0;
        }
        if (dataParts.length > DATA_COUNT) {
            return 2;
        } else {
            return 3;
        }
    }

    // метод для обработки кода ошибки, полученного при проверке данных по количеству
    public static void printErrorCode(int errorCode) {
        switch (errorCode) {
            case (0) -> System.out.println("_____".repeat(5));
            case (1) -> System.out.println("Введена пустая строка!");
            case (2) -> System.out.println("Вы ввели больше данных, чем требуется!");
            case (3) -> System.out.println("Вы ввели меньше данных, чем требуется!");
        }
    }

    // метод для парсинга полученных от пользователя данных
    public static User parseData(String data) throws GenderFormatException, ParseException, NumberFormatException {
        User newUser = new User(); // создаём экземпляр класса User для записи вводимых данных пользователя
        String[] dataParts = data.split(" ");
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy"); // создаём нужный формат для даты

        // В данном методе при возникновении исключения выводим соответствующее сообщение на англ. языке
        // об ошибке сразу при обработке, а также пробрасываем на уровень выше для повторной обработки в методе Main
        // и вывода дополнительного сообщения об ошибке на русском
        for (String dataPart : dataParts) {
            if (dataPart.length() == 1) {
                newUser.setGender(dataPart.charAt(0));
                if (!(newUser.getGender() == 'm' | newUser.getGender() == 'M' |
                        newUser.getGender() == 'f' | newUser.getGender() == 'F')) {
                    System.out.println("Invalid gender format entered!\n");
                    throw new GenderFormatException("Неверно введены данные о поле!");
                }
            } else if (dataPart.contains(".")) {
                try {
                    newUser.setDate(format.parse(dataPart));
                } catch (ParseException e) {
                    System.out.println("Invalid date format entered!\n" + Arrays.toString(e.getStackTrace()));
                    throw new ParseException("Данные о дате рождения введены некорректно!", e.getErrorOffset());
                }
            } else if (dataPart.contains("0") | dataPart.contains("1") | dataPart.contains("2") |
                    dataPart.contains("3") | dataPart.contains("4") | dataPart.contains("5") | dataPart.contains("6") |
                    dataPart.contains("7") | dataPart.contains("8") | dataPart.contains("9")) {
                try {
                    newUser.setPhoneNumber(Long.parseLong(dataPart));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid phone number format entered!\n" + Arrays.toString(e.getStackTrace()));
                    throw new NumberFormatException("Некорректный ввод номера телефона!");
                }
            } else if (newUser.getSurname().equals("undefined")) {
                newUser.setSurname(dataPart);
            } else if (newUser.getName().equals("undefined")) {
                newUser.setName(dataPart);
            } else {
                newUser.setPatronymic(dataPart);
            }
        }

        System.out.println();
        System.out.println("Вы указали:");
        System.out.println("Фамилия: " + newUser.getSurname());
        System.out.println("Имя: " + newUser.getName());
        System.out.println("Отчество: " + newUser.getPatronymic());
        System.out.println("Дата рождения: " + format.format(newUser.getDate()));
        System.out.println("Номер телефона: " + newUser.getPhoneNumber());
        System.out.println("Пол: " + newUser.getGender());

        return newUser;
    }

    // метод для записи в файл данных пользователя
    public static void writeFile(User user) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        File file = new File(user.getSurname() + ".txt");
        FileWriter fr = null;

        try {
            if (file.exists()) {
                fr = new FileWriter(file, true);
                fr.append("\n<").append(user.getSurname()).append(">").append("<").append(user.getName()).append(">").
                        append("<").append(user.getPatronymic()).append(">").append("<").
                        append(format.format(user.getDate())).append(">").append("<").
                        append(String.valueOf(user.getPhoneNumber())).append(">").append("<").
                        append(String.valueOf(user.getGender())).append(">");
            } else {
                file.createNewFile();
                fr = new FileWriter(file, false);
                fr.write("<" + user.getSurname() + ">" + "<" + user.getName() + ">" + "<" +
                        user.getPatronymic() + ">" + "<" + format.format(user.getDate()) + ">" +
                        "<" + user.getPhoneNumber() + ">" + "<" + user.getGender() + ">");
            }
        } catch (IOException e) {
            System.out.println("Не удаётся создать файл!");
            System.out.println(Arrays.toString(e.getStackTrace()));
        } finally {
            try {
                fr.flush();
                fr.close();
            } catch (NullPointerException e) {
                System.out.println("Возникла ошибка NullPointerException при ссылке на несуществующий объект!");
                System.out.println(Arrays.toString(e.getStackTrace()));
            } catch (IOException e) {
                System.out.println("Не удаётся закрыть файл!");
                System.out.println(Arrays.toString(e.getStackTrace()));
            }
        }
        System.out.println("Данные пользователя успешно сохранены в файле!");
    }

    // метод для запроса у пользователя необходимости продолжения ввода данных
    public static String askForContinue() {
        System.out.println("""
                ____________________________________________________________
                Продолжить ввод данных?
                (Yes / No)
                _____________________________________________________________
                """);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}