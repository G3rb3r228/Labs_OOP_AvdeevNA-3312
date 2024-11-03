import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.awt.event.*;

public class App {
    /**
     * Initializes and displays the Coffeeshop application window.
     * The window includes:
     * - A panel with buttons.
     * - The search field.
     * - A table for displaying data about the coffee shop.
     */
    public static void CoffeeShop(){
        // Создание главного окна приложения
        JFrame frame = new JFrame("Coffee Shop");

        // Настройка действия при закрытии окна
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 600); // Установка размера окна

        // Настройка цвета фона окна
        frame.getContentPane().setBackground(new Color(111, 78, 55));

        // Главная панель, которая содержит кнопки и панель поиска
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout()); // Использует BorderLayout для упорядочивания кнопок и поиска

        // Панель для кнопок
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(new Color(30, 2, 2));

        // Массивы с названиями и иконками для кнопок
        String[] icons = {"save", "open", "add", "edit", "bin", "print"};
        String[] buttonsName = {"Сохранить", "Открыть", "Добавить", "Редактировать", "Удалить", "Печать списка"};
        JButton[] buttons = new JButton[icons.length];

        // Добавляем кнопки на панель кнопок
        for (int i = 0; i < icons.length; i++) {
            ImageIcon iconImage = new ImageIcon(new ImageIcon(Objects.requireNonNull(App.class.getResource("icons/" + icons[i] + ".png")))
                    .getImage().getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH));
            buttons[i] = new JButton(buttonsName[i], iconImage);
            buttonPanel.add(buttons[i]);
        }



        /**
         * Обработчик события нажатия на кнопку "Удалить".
         * При нажатии на эту кнопку выполняется удаление выбранного элемента.
         * Логика удаления в данном примере не реализована.
         * Необходимо добавить код для удаления соответствующего элемента
         * (например, строки из таблицы, объекта из списка и т.д.).
         * После успешного удаления выводится диалоговое окно с сообщением.
         */
        buttons[4].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Элемент удалён");
            }
        });


        // Создание панели для поиска
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.RIGHT)); // Выравниваем по правому краю
        searchPanel.setBackground(new Color(30, 2, 2));

        // Поле для ввода текста поиска
        JTextField searchField = new JTextField(15);
        JButton searchButton = new JButton("Поиск"); // Кнопка для поиска


        /**
         * Обработчик события нажатия на кнопку поиска.
         * При нажатии на кнопку запускается поиск по таблице по введенному тексту.
         * Текст для поиска берется из поля `searchField`.
         * После выполнения поиска выводится диалоговое окно с результатами.
         */
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    checkName(searchField); // Вызываем функцию проверки

                    // Пример вывода результата поиска (замените на свою логику)
                    JOptionPane.showMessageDialog(null, "Найденные результаты: " + searchField.getText());
                } catch (NullPointerException ex) {
                    JOptionPane.showMessageDialog(null, "Вы не ввели текст для поиска");
                } catch (MyException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        // Добавляем текст плейсхолдера в поле поиска
        searchField.setText("Поиск");
        searchField.setForeground(Color.GRAY); // По умолчанию серый текст

        // Добавляем FocusListener для обработки фокуса поля
        /**
         * Добавляет обработчик событий для текстового поля поиска.
         * - При получении фокуса текст становится черным, и плейсхолдер исчезает.
         * - При потере фокуса, если поле пустое, плейсхолдер возвращается.
         * Использует FocusAdapter — адаптерный класс с пустыми реализациями методов интерфейса FocusListener,
         * включающего focusGained() и focusLost().
         */
        searchField.addFocusListener(new FocusAdapter() {
            @Override
            /**
             * Метод срабатывает, когда текстовое поле поиска получает фокус.
             * - Если текстовое поле содержит "Поиск" (плейсхолдер), этот текст удаляется и цвет текста изменяется на черный.
             *
             * @param e - событие фокуса.
             */
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Поиск")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK); // Цвет текста черный при вводе
                }
            }

            @Override
            /**
             * Метод срабатывает, когда текстовое поле поиска теряет фокус.
             * - Если поле пустое, оно заполняется текстом плейсхолдера "Поиск", а цвет текста изменяется на серый.
             *
             * @param e - событие фокуса.
             */
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setForeground(Color.GRAY);
                    searchField.setText("Поиск"); // Возвращаем плейсхолдер
                }
            }
        });

        // Добавляем поле поиска и кнопку в панель поиска
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Добавляем панели кнопок и поиска в верхнюю панель
        topPanel.add(buttonPanel, BorderLayout.CENTER);
        topPanel.add(searchPanel, BorderLayout.EAST); // Панель поиска справа

        // Создание таблицы для отображения данных
        Object[][] data = {
                {"", "", "", ""},
                {"", "", "", ""},
                {"", "", "", ""},
                {"", "", "", ""},
                {"", "", "", ""},
                {"", "", "", ""}
        };

        String[] columnNames = {"Сорт кофе", "Страна произрастания", "Степень обжарки", "Оценка Q-грейдера"};

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model); // Таблица с пустыми данными

        table.setBackground(new Color(164, 127, 102));
        table.setFillsViewportHeight(true); // Растягиваем таблицу на всю доступную высоту
        table.setFont(new Font("Arial", Font.PLAIN, 14));

        // Устанавливаем цвет разделителей
        table.setShowGrid(true);
        table.setGridColor(Color.BLACK); // например, устанавливаем красный цвет

        // Настраиваем заголовок таблицы
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(30, 2, 2));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 16));

        // Обработчик события для кнопки "Сохранить"
        buttons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog save = new FileDialog(frame, "Сохранение данных", FileDialog.SAVE);
                save.setFile("*.txt");

                save.setVisible(true);

                // Правильное получение имени файла
                String directory = save.getDirectory();
                String file = save.getFile();

                String fileName = (directory != null ? directory : "") + (file != null ? file : "");

                // Если пользователь нажал "Отмена", выходим из метода
                if (fileName.isEmpty()) {
                    return;
                };

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                    // Запись данных в файл
                    for (int i = 0; i < model.getRowCount(); i++) {
                        for (int j = 0; j < model.getColumnCount(); j++) {
                            writer.write((String) model.getValueAt(i, j));
                            if (j < model.getColumnCount() - 1) { // Добавляем разделитель, кроме последнего столбца
                                writer.write("\n"); // Используем запятую как разделитель, можно заменить на любой другой
                            }
                        }
                        writer.newLine(); // Переход на новую строку после каждой строки данных
                    }

                    writer.close();
                    JOptionPane.showMessageDialog(null, "Данные сохранены успешно");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Ошибка при записи в файл: " + ex.getMessage());
                }
            }
        });


        buttons[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog open = new FileDialog(frame, "Выберите файл", FileDialog.LOAD);
                open.setVisible(true);

                String fileName = open.getDirectory() + open.getFile();

                if (fileName.isEmpty()) {
                    return; // Пользователь отменил выбор файла
                }

                try {
                    BufferedReader reader = new BufferedReader(new FileReader(fileName));
                    int rows = model.getRowCount();
                    for (int i = 0; i < rows; i++) model.removeRow(0); // Очистка таблицы

                    String type;
                    do{
                        type = reader.readLine();
                        if(type != null){
                            String country = reader.readLine();
                            String roast = reader.readLine();
                            String grade = reader.readLine();
                            model.addRow(new String[]{type, country, roast, grade});
                        }
                    }while(type != null);
                    reader.close();

                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Файл не найден: " + fileName);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Ошибка при чтении файла: " + ex.getMessage());
                }
            }
        });

        // Добавляем таблицу в JScrollPane для прокрутки
        JScrollPane scrollPane = new JScrollPane(table);

        // Добавляем верхнюю панель и таблицу в окно
        frame.add(topPanel, BorderLayout.NORTH); // Добавляем объединённую верхнюю панель
        frame.add(scrollPane, BorderLayout.CENTER); // Таблица в центре окна

        // Делаем окно видимым
        frame.setVisible(true);


    }

    private static class MyException extends Exception {
        public MyException() {
            super ("Вы не ввели название сорта кофе для поиска");
        }}

    private static void checkName(JTextField cName) throws MyException, NullPointerException {
        String sName = cName.getText();
        if (sName.contains("Поиск")) throw new MyException();
        if (sName.isEmpty()) throw new NullPointerException();
    }


    /**
     * Laboratory work №5.
     *
     * @author Avdeev Nikita;
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::CoffeeShop);
    }
}
