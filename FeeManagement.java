import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class Student {
    private int id;
    private String name;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

class FeeRecord {
    private int studentId;
    private double amount;

    public FeeRecord(int studentId, double amount) {
        this.studentId = studentId;
        this.amount = amount;
    }

    public int getStudentId() {
        return studentId;
    }

    public double getAmount() {
        return amount;
    }
}

class Payment {
    private int studentId;
    private double amount;

    public Payment(int studentId, double amount) {
        this.studentId = studentId;
        this.amount = amount;
    }

    public int getStudentId() {
        return studentId;
    }

    public double getAmount() {
        return amount;
    }
}

class FeeManagementSystemGUI extends JFrame {

    private DefaultListModel<String> feeListModel;
    private DefaultListModel<String> paymentListModel;
    private JTextArea balanceTextArea;

    private List<Student> students;
    private List<FeeRecord> feeRecords;
    private List<Payment> payments;

    public FeeManagementSystemGUI() {
        setTitle("Fee Management System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        students = new ArrayList<>();
        feeRecords = new ArrayList<>();
        payments = new ArrayList<>();

        feeListModel = new DefaultListModel<>();
        paymentListModel = new DefaultListModel<>();

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel feePanel = createFeePanel();
        JPanel paymentPanel = createPaymentPanel();
        JPanel balancePanel = createBalancePanel();

        tabbedPane.addTab("Fee Records", feePanel);
        tabbedPane.addTab("Payments", paymentPanel);
        tabbedPane.addTab("Balance", balancePanel);

        add(tabbedPane);

        setVisible(true);
    }

    private JPanel createFeePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JList<String> feeList = new JList<>(feeListModel);
        panel.add(new JScrollPane(feeList), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));

        JTextField studentIdField = new JTextField();
        JTextField amountField = new JTextField();
        JButton addButton = new JButton("Add Fee Record");

        inputPanel.add(new JLabel("Student ID:"));
        inputPanel.add(studentIdField);
        inputPanel.add(new JLabel("Amount:"));
        inputPanel.add(amountField);
        inputPanel.add(new JLabel());
        inputPanel.add(addButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int studentId = Integer.parseInt(studentIdField.getText());
                    double amount = Double.parseDouble(amountField.getText());

                    feeRecords.add(new FeeRecord(studentId, amount));
                    updateFeeList();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter valid numbers.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(inputPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createPaymentPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JList<String> paymentList = new JList<>(paymentListModel);
        panel.add(new JScrollPane(paymentList), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));

        JTextField studentIdField = new JTextField();
        JTextField amountField = new JTextField();
        JButton addButton = new JButton("Add Payment");

        inputPanel.add(new JLabel("Student ID:"));
        inputPanel.add(studentIdField);
        inputPanel.add(new JLabel("Amount:"));
        inputPanel.add(amountField);
        inputPanel.add(new JLabel());
        inputPanel.add(addButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int studentId = Integer.parseInt(studentIdField.getText());
                    double amount = Double.parseDouble(amountField.getText());

                    payments.add(new Payment(studentId, amount));
                    updatePaymentList();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter valid numbers.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(inputPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createBalancePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        balanceTextArea = new JTextArea();
        balanceTextArea.setEditable(false);
        panel.add(new JScrollPane(balanceTextArea), BorderLayout.CENTER);

        JButton calculateButton = new JButton("Calculate Balance");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int studentId = Integer.parseInt(JOptionPane.showInputDialog("Enter Student ID:"));
                    double balance = calculateBalance(studentId);
                    balanceTextArea.setText("Balance for Student ID " + studentId + ": $" + balance);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number for Student ID.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(calculateButton, BorderLayout.SOUTH);

        return panel;
    }

    private void updateFeeList() {
        feeListModel.clear();
        for (FeeRecord feeRecord : feeRecords) {
            feeListModel.addElement("Student ID: " + feeRecord.getStudentId() + ", Amount: $" + feeRecord.getAmount());
        }
    }

    private void updatePaymentList() {
        paymentListModel.clear();
        for (Payment payment : payments) {
            paymentListModel.addElement("Student ID: " + payment.getStudentId() + ", Amount: $" + payment.getAmount());
        }
    }

    private double calculateBalance(int studentId) {
        double totalFees = 0;
        double totalPayments = 0;

        for (FeeRecord feeRecord : feeRecords) {
            if (feeRecord.getStudentId() == studentId) {
                totalFees += feeRecord.getAmount();
            }
        }

        for (Payment payment : payments) {
            if (payment.getStudentId() == studentId) {
                totalPayments += payment.getAmount();
            }
        }

        return totalFees - totalPayments;
    }
}

public class FeeManagement {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FeeManagementSystemGUI();
            }
        });
    }
}
