package lab2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainFrame extends JFrame {

	private static final int WIDTH = 1100;
	private static final int HEIGHT = 500;

	private JTextField textFieldX;
	private JTextField textFieldY;
	private JTextField textFieldZ;
	private JTextField textFieldResult;

	private ButtonGroup radioButtons = new ButtonGroup();
	private ButtonGroup memButtons = new ButtonGroup();

	private Box hboxFormulaType = Box.createHorizontalBox();
	private Box hboxForMem = Box.createHorizontalBox();

	private int formulaId = 0;

	private Double mem1 = new Double(0);
	private Double mem2 = new Double(0);
	private Double mem3 = new Double(0);

	private int memId = 0;

	public Double calculate1(Double x, Double y, Double z) {
		if (Math.log(Math.pow(1+z, 2) + Math.cos(Math.PI*Math.pow(y, 3))) < 0) {
            JOptionPane.showMessageDialog(MainFrame.this,
                    "Число под корнем должно быть больше нуля.", "Ошибка ввода", JOptionPane.WARNING_MESSAGE);
            return 0.0;
        }
		if (x <= 0) {
            JOptionPane.showMessageDialog(MainFrame.this,
                    "x должен быть больше нуля.", "Ошибка ввода", JOptionPane.WARNING_MESSAGE);
            return 0.0;
        }
		if (Math.cos(Math.exp(x)) + Math.sqrt(1/x) + Math.exp(Math.pow(x, 2)) == 0) {
            JOptionPane.showMessageDialog(MainFrame.this,
                    "Число под корнем должно быть больше нуля.", "Ошибка ввода", JOptionPane.WARNING_MESSAGE);
            return 0.0;
        }
		return Math.pow(Math.log(Math.pow(1+z, 2)) + Math.cos(Math.PI * Math.pow(y, 3)), 1/4)/Math.pow(Math.cos(Math.exp(x)) + 1/Math.sqrt(x) + Math.exp(Math.pow(x, 2)), Math.sin(y));
	}

	public Double calculate2(Double x, Double y, Double z) {
		if (y < 0) {
            JOptionPane.showMessageDialog(MainFrame.this,
                    "у должен быть не меньше нуля", "Ошибка ввода", JOptionPane.WARNING_MESSAGE);
            return 0.0;
        }
		if (z == 0) {
            JOptionPane.showMessageDialog(MainFrame.this,
                    "z не должен равняться нулю.", "Ошибка ввода", JOptionPane.WARNING_MESSAGE);
            return 0.0;
        }
		if (x + y <= 0) {
            JOptionPane.showMessageDialog(MainFrame.this,
                    "'x + y' должен быть больше нуля.", "Ошибка ввода", JOptionPane.WARNING_MESSAGE);
            return 0.0;
        }
		return (Math.tan(Math.pow(x, 2)) + Math.sqrt(y)) / z * Math.log10(x + y);
	}

	private void addRadioButton(String buttonName, final int formulaId) {

		JRadioButton button = new JRadioButton(buttonName);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				MainFrame.this.formulaId = formulaId;
			}
		});
		radioButtons.add(button);
		hboxFormulaType.add(button);
	}

	private void addMemButtons(String buttonName, final int memId) {

		JRadioButton button = new JRadioButton(buttonName);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				MainFrame.this.memId = memId;
				if (memId == 1) {
					textFieldResult.setText(mem1.toString());
				} else if (memId == 2) {
					textFieldResult.setText(mem2.toString());
				} else if (memId == 3) {
					textFieldResult.setText(mem3.toString());
				}
			}
		});

		memButtons.add(button);
		hboxForMem.add(button);
	}

	public MainFrame() {
		super("Вычисление формулы");
		setSize(WIDTH, HEIGHT);
		Toolkit kit = Toolkit.getDefaultToolkit();

		setLocation((kit.getScreenSize().width - WIDTH) / 2, (kit.getScreenSize().height - HEIGHT) / 2);

		hboxFormulaType.add(Box.createHorizontalGlue());
		addRadioButton("Формула 1", 1);
		hboxFormulaType.add(Box.createHorizontalStrut(5));
		addRadioButton("Формула 2", 2);
		hboxFormulaType.add(Box.createHorizontalGlue());
		hboxFormulaType.setBorder(BorderFactory.createLineBorder(Color.cyan));

		JLabel labelForX = new JLabel("X:");
		textFieldX = new JTextField("0", 10);
		textFieldX.setMaximumSize(textFieldX.getPreferredSize());

		JLabel labelForY = new JLabel("Y:");
		textFieldY = new JTextField("0", 10);
		textFieldY.setMaximumSize(textFieldY.getPreferredSize());

		JLabel labelForZ = new JLabel("Z:");
		textFieldZ = new JTextField("0", 10);
		textFieldZ.setMaximumSize(textFieldZ.getPreferredSize());


		Box hboxVariables = Box.createHorizontalBox();
		hboxVariables.setBorder(BorderFactory.createLineBorder(Color.RED));
		hboxVariables.add(Box.createHorizontalGlue());
		hboxVariables.add(labelForX);
		hboxVariables.add(Box.createHorizontalStrut(10));
		hboxVariables.add(textFieldX);
		hboxVariables.add(Box.createHorizontalStrut(100));
		hboxVariables.add(labelForY);
		hboxVariables.add(Box.createHorizontalStrut(10));
		hboxVariables.add(textFieldY);
		hboxVariables.add(Box.createHorizontalStrut(100));
		hboxVariables.add(labelForZ);
		hboxVariables.add(Box.createHorizontalStrut(10));
		hboxVariables.add(textFieldZ);
		hboxVariables.add(Box.createHorizontalGlue());

		JLabel labelForResult = new JLabel("Результат");

		textFieldResult = new JTextField("0", 15);
		textFieldResult.setMaximumSize(textFieldResult.getPreferredSize());

		Box hboxResult = Box.createHorizontalBox();
		hboxResult.add(Box.createHorizontalGlue());
		hboxResult.add(labelForResult);
		hboxResult.add(Box.createHorizontalStrut(10));
		hboxResult.add(textFieldResult);
		hboxResult.add(Box.createHorizontalGlue());
		hboxResult.setBorder(BorderFactory.createLineBorder(Color.BLUE));

		JButton buttonCal = new JButton("Вычислить");
		buttonCal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				try {
					Double x = Double.parseDouble(textFieldX.getText());
					Double y = Double.parseDouble(textFieldY.getText());
					Double z = Double.parseDouble(textFieldZ.getText());
					Double result = null;

					if (formulaId == 1) {
						result = calculate1(x, y, z);
					} else if (formulaId == 2) {
						result = calculate2(x, y, z);
					} else {
						JOptionPane.showMessageDialog(MainFrame.this,
								"Выберите пожалуйста формулу",
								"Ошибка выбора формулы",
								JOptionPane.ERROR_MESSAGE);
					}

					if (result != null) {
						textFieldResult.setText(result.toString());
					}
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(MainFrame.this,
							"Ошибка в формате записи числа с плавающей точкой",
							"Ошибочный формат числа",
							JOptionPane.WARNING_MESSAGE);

				}
			}
		});

		JButton buttonReset = new JButton("Очистить поля");
		buttonReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				textFieldX.setText("0");
				textFieldY.setText("0");
				textFieldZ.setText("0");
				textFieldResult.setText("0");
			}
		});

		Box hboxButtons = Box.createHorizontalBox();
		hboxButtons.add(Box.createHorizontalGlue());
		hboxButtons.add(buttonCal);
		hboxButtons.add(Box.createHorizontalStrut(30));
		hboxButtons.add(buttonReset);
		hboxButtons.add(Box.createHorizontalGlue());
		hboxButtons.setBorder(BorderFactory.createLineBorder(Color.GREEN));

		hboxForMem.add(Box.createHorizontalGlue());
		addMemButtons("Переменная 1", 1);
		addMemButtons("Переменная 2", 2);
		addMemButtons("Перепенная 3", 3);
		hboxForMem.add(Box.createHorizontalGlue());

		JButton buttonM = new JButton("M+");
		buttonM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				Double x = Double.parseDouble(textFieldX.getText());
				Double y = Double.parseDouble(textFieldY.getText());
				Double z = Double.parseDouble(textFieldZ.getText());
				if (memId == 1) {
					if (formulaId == 1) {
						mem1 += calculate1(x,y,z);
					}
					if (formulaId == 2) {
						mem1 += calculate2(x,y,z);
					}
					textFieldResult.setText(mem1.toString());
				} else if (memId == 2) {
					if (formulaId == 1) {
						mem2 += calculate1(x,y,z);
					}
					if (formulaId == 2) {
						mem2 += calculate2(x,y,z);
					}
					textFieldResult.setText(mem2.toString());
				} else if (memId == 3) {
					if (formulaId == 1) {
						mem3 += calculate1(x,y,z);
					}
					if (formulaId == 2) {
						mem3 += calculate2(x,y,z);
					}
					textFieldResult.setText(mem3.toString());
				} else {
					JOptionPane.showMessageDialog(MainFrame.this,
							"Выберите пожалуйста записывающую память",
							"Ошибка в выборе памяти",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		JButton buttonMC = new JButton("MC");
		buttonMC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				if (memId == 1) {
					mem1 = Double.parseDouble("0");
					textFieldResult.setText("0");
				} else if (memId == 2) {
					mem2 = Double.parseDouble("0");
					textFieldResult.setText("0");
				} else if (memId == 3) {
					mem3 = Double.parseDouble("0");
					textFieldResult.setText("0");
				} else {
					JOptionPane.showMessageDialog(MainFrame.this,
							"Выберите пожалуйста записывающую память",
							"Ошибка в выборе памяти",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		Box hboxForBut = Box.createHorizontalBox();
		hboxForBut.add(Box.createHorizontalGlue());
		hboxForBut.add(buttonM);
		hboxForBut.add(Box.createHorizontalStrut(20));
		hboxForBut.add(buttonMC);
		hboxForBut.add(Box.createHorizontalGlue());
		
		
		Box contentBox = Box.createVerticalBox();
		contentBox.add(Box.createVerticalGlue());
		contentBox.add(hboxFormulaType);
		contentBox.add(Box.createVerticalGlue());
		contentBox.add(hboxVariables);
		contentBox.add(Box.createVerticalGlue());
		contentBox.add(hboxResult);
		contentBox.add(Box.createVerticalGlue());
		contentBox.add(hboxButtons);
		contentBox.add(Box.createVerticalGlue());
		contentBox.add(hboxForMem);
		contentBox.add(Box.createVerticalGlue());
		contentBox.add(hboxForBut);
		contentBox.add(Box.createVerticalGlue());

		getContentPane().add(contentBox, BorderLayout.CENTER);

	}

	public static void main(String[] args) {

		MainFrame frame = new MainFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}