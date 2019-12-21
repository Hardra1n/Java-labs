package lab3; // Holla amigo

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Аргументы командной строки - коэффициенты многочлена , пример :
 *
 * <p>Аргементы командной строки : 5 4 3 2 1</p>
 * <p>Многочлен : 5*x^4 + 4*x^3 + 3*x^2 + 2*x + 1</p>
 */

public class MainFrame extends JFrame {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 500;
    
    private Double[] coefficients;

    private JMenuItem aboutProgramItem;

    private JTextField textFieldFrom;
    private JTextField textFieldTo;
    private JTextField textFieldStep;

    private Box hboxResult;

    private GornerTableCellRenderer renderer = new GornerTableCellRenderer();

    private GornerTableModel data;

    public MainFrame(Double[] coefficients) {
        super("Табулирование многочлена на отрезке по схеме Горнера");
        this.coefficients = coefficients;

        setSize(WIDTH, HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();


        setLocation((kit.getScreenSize().width - WIDTH) / 2, (kit.getScreenSize().height - HEIGHT) / 2);

        JMenuBar menuBar = new JMenuBar();

        setJMenuBar(menuBar);
        JMenu referenceMenu = new JMenu("Справка");

        menuBar.add(referenceMenu);
        
        Action aboutProgramAction = new AbstractAction("О программе") {
            public void actionPerformed(ActionEvent ev) {

                Box mainPanel = Box.createVerticalBox();

                Box title1 = Box.createHorizontalBox();
                JLabel l1 = new JLabel("6 группа");
                l1.setFont(new Font("TimesRoman", Font.ITALIC, 20));
                title1.add(Box.createHorizontalGlue());
                title1.add(l1);
                title1.add(Box.createHorizontalGlue());


                Box title2 = Box.createHorizontalBox();
                JLabel l2 = new JLabel("Хо Ван Кирилл");
                l2.setFont(new Font("TimesRoman", Font.ITALIC, 20));
                title2.add(Box.createHorizontalGlue());
                title2.add(l2);
                title2.add(Box.createHorizontalStrut(10));
                title2.add(Box.createHorizontalGlue());
                


                mainPanel.add(Box.createVerticalGlue());
                mainPanel.add(title1);
                mainPanel.add(Box.createVerticalStrut(10));
                mainPanel.add(title2);
                mainPanel.add(Box.createVerticalStrut(10));
                mainPanel.add(Box.createVerticalGlue());


                JOptionPane.showMessageDialog(MainFrame.this,
                        mainPanel, "О программе", JOptionPane.INFORMATION_MESSAGE);
            }
        };

        aboutProgramItem = referenceMenu.add(aboutProgramAction);
        
        JLabel labelForFrom = new JLabel("X изменяется на интервале от:");
        textFieldFrom = new JTextField("0.0", 10);
        textFieldFrom.setMaximumSize(textFieldFrom.getPreferredSize());

        JLabel labelForTo = new JLabel("до:");
        textFieldTo = new JTextField("1.0", 10);
        textFieldTo.setMaximumSize(textFieldTo.getPreferredSize());

        JLabel labelForStep = new JLabel("с шагом:");
        textFieldStep = new JTextField("0.1", 10);
        textFieldStep.setMaximumSize(textFieldStep.getPreferredSize());

        Box hboxRange = Box.createHorizontalBox();
        hboxRange.setBorder(BorderFactory.createBevelBorder(1));
        hboxRange.add(Box.createHorizontalGlue());
        hboxRange.add(labelForFrom);
        hboxRange.add(Box.createHorizontalStrut(10));
        hboxRange.add(textFieldFrom);
        hboxRange.add(Box.createHorizontalStrut(20));
        hboxRange.add(labelForTo);
        hboxRange.add(Box.createHorizontalStrut(10));
        hboxRange.add(textFieldTo);
        hboxRange.add(Box.createHorizontalStrut(20));
        hboxRange.add(labelForStep);
        hboxRange.add(Box.createHorizontalStrut(10));
        hboxRange.add(textFieldStep);
        hboxRange.add(Box.createHorizontalGlue());

        hboxRange.setPreferredSize(new Dimension(
                new Double(hboxRange.getMaximumSize().getWidth()).intValue(),
                new Double(hboxRange.getMinimumSize().getHeight()).intValue() * 2));
        getContentPane().add(hboxRange, BorderLayout.NORTH);

        JButton buttonCalc = new JButton("Вычислить");

        buttonCalc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    Double from = Double.parseDouble(textFieldFrom.getText());
                    Double to = Double.parseDouble(textFieldTo.getText());
                    Double step = Double.parseDouble(textFieldStep.getText());

                    data = new GornerTableModel(from, to, step, MainFrame.this.coefficients);

                    JTable table = new JTable(data);
                    table.setDefaultRenderer(Double.class, renderer);
                    table.setRowHeight(30);

                    hboxResult.removeAll();
                    hboxResult.add(new JScrollPane(table));

                    getContentPane().validate();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this,
                            "Ошибка в формате записи числа с плавающей точкой",
                            "Ошибочный формат числа", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        JButton buttonReset = new JButton("Очистить поля");

        buttonReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                textFieldFrom.setText("0.0");
                textFieldTo.setText("1.0");
                textFieldStep.setText("0.1");

                hboxResult.removeAll();
                hboxResult.add(new JPanel());

                getContentPane().validate();

            }
        });

        Box hboxButtons = Box.createHorizontalBox();
        hboxButtons.setBorder(BorderFactory.createBevelBorder(1));
        hboxButtons.add(Box.createHorizontalGlue());
        hboxButtons.add(buttonCalc);
        hboxButtons.add(Box.createHorizontalStrut(30));
        hboxButtons.add(buttonReset);
        hboxButtons.add(Box.createHorizontalGlue());
        hboxButtons.setPreferredSize(new Dimension(
                new Double(hboxButtons.getMaximumSize().getWidth()).intValue(),
                new Double(hboxButtons.getMinimumSize().getHeight()).intValue() * 2));

        getContentPane().add(hboxButtons, BorderLayout.SOUTH);

        hboxResult = Box.createHorizontalBox();
        hboxResult.add(new JPanel());

        getContentPane().add(hboxResult, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Невозможно табулировать многочлен, для которого не задано ни одного коэффициента!");
            System.exit(-1);
        }

        Double[] coefficients = new Double[args.length];
        int i = 0;

        try {
            for (String arg : args) {
                coefficients[i++] = Double.parseDouble(arg);
            }
        } catch (NumberFormatException ex) {
            System.out.println("Ошибка преобразования строки '" + args[i] + "' в число типа Double");
            System.exit(-2);
        }

        MainFrame frame = new MainFrame(coefficients);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}