package lab3;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

public class GornerTableCellRenderer implements TableCellRenderer {

    private JPanel panel = new JPanel();
    private JLabel label = new JLabel();

    private String needle = null;
    private String from = null;
    private String to = null;

    private DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance();

    public GornerTableCellRenderer() {
        formatter.setMaximumFractionDigits(5);
        formatter.setGroupingUsed(false);
        DecimalFormatSymbols dottedDouble = formatter.getDecimalFormatSymbols();
        dottedDouble.setDecimalSeparator('.');
        formatter.setDecimalFormatSymbols(dottedDouble);
        panel.add(label);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int col) {
    	panel.setBackground(Color.WHITE);
       if (col == 2) {
    	   if (value.toString() == "true") {
       		panel.setBackground(Color.BLUE);
    	   }
    	   label.setText(value.toString());;
    	   return panel;
       }
    	String formattedDouble = formatter.format(value);

        label.setText(formattedDouble);

        if ((col == 1) && needle != null && needle.equals(formattedDouble)) {
            panel.setBackground(Color.RED);
        } else {
        	String str1 = "0", str2 = "0";
        	boolean flag = false;
        	for (int i = 0; i < formattedDouble.length(); i++) {
        		if (formattedDouble.charAt(i) == '.') {
        			flag = true;
        			continue;
        		}
        		if (flag == false) 
        			str1 += formattedDouble.charAt(i);
        		else
        			str2+= formattedDouble.charAt(i);	
        	}
        	int a = Integer.parseInt(str1), b = Integer.parseInt(str2);
        	System.out.println(a + " " + b);
        	if (a - b == 0) {
        		panel.setBackground(Color.BLUE);
        	}
        }
        return panel;
    }

    public void setNeedle(String needle) {
        this.needle = needle;
    }

    public void setRanges(String from, String to) {
        this.from = from;
        this.to = to;
    }

}