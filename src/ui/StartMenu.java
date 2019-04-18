package ui;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;

public class StartMenu extends JFrame {

    private JPanel usersPanel;
    private JPanel buttonsPanel;
    private JLabel headingLabel;
    private JList<String> usersList;
    private JButton loginButton;
    private JButton deleteUserButton;
    private JButton addUserButton;
    private File file;

    private ArrayList<String> usersNames;

    final private int width = 840;
    final private int height = 360;
    final private int usersPanelHeight = 240;
    final private int labelWidth = 60;
    final private int labelHeight = 20;

    final private int buttonsPanelHeight = height - usersPanelHeight;
    final private int margin = 80;
    final private int buttonHeight = buttonsPanelHeight / 3;
    final private int buttonWidth = (width - 3 * margin) / 3;

    public StartMenu() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((dim.width - width) / 2, (dim.height - height) / 2, width, height);
        setTitle("Chicken Invaders");
        getContentPane().setBackground(Color.LIGHT_GRAY);
        setResizable(false);
        setLayout(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitForm(e);
            }
        });
        initializeUsersPanel();
        getContentPane().add(usersPanel);
        initializeButtonsPanel();
        getContentPane().add(buttonsPanel);

        setVisible(true);
    }

    private void initializeUsersPanel() {
        usersPanel = new JPanel();
        usersPanel.setBounds(0, 0, width, usersPanelHeight);
        usersPanel.setLayout(null);
        usersPanel.setOpaque(false);
        headingLabel = new JLabel("کاربران");
        headingLabel.setHorizontalAlignment(JLabel.CENTER);
        headingLabel.setBounds((width - labelWidth) / 2, 0, labelWidth, labelHeight);
        usersPanel.add(headingLabel);
        getUsersName();
        usersList = new JList<>();
        loadNamesToJList();
        DefaultListCellRenderer renderer = (DefaultListCellRenderer) usersList.getCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        usersList.setOpaque(false);
        JScrollPane jsp = new JScrollPane(usersList);
        jsp.setBounds(margin, headingLabel.getHeight(), width - 2 * margin, usersPanelHeight - headingLabel.getHeight());
        jsp.setOpaque(false);
        usersPanel.add(jsp);
    }

    private void loadNamesToJList() {
        usersList.setListData(usersNames.toArray(new String[usersNames.size()]));
    }

    private void getUsersName() {
        JSONObject data = null;
        try {
            data = (JSONObject) (new JSONParser().parse(new FileReader("data/game.data")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONArray namesArray = (JSONArray) data.get("Names");
        usersNames = new ArrayList<>();
        for (int i = 0; i < namesArray.size(); i++)
            usersNames.add(namesArray.get(i).toString());
    }

    private void initializeButtonsPanel() {
        buttonsPanel = new JPanel();
        buttonsPanel.setBounds(0, usersPanelHeight, width, buttonsPanelHeight);
        buttonsPanel.setLayout(null);
        buttonsPanel.setOpaque(false);
        loginButton = new JButton("ورود");
        loginButton.setBounds(margin / 2, buttonHeight, buttonWidth, buttonHeight);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!usersList.isSelectionEmpty())
                    new PlayerMenu(usersList.getSelectedValue());
            }
        });
        buttonsPanel.add(loginButton);
        deleteUserButton = new JButton("حذف کاربر");
        deleteUserButton.setBounds(loginButton.getX() + loginButton.getWidth() + margin, buttonHeight, buttonWidth, buttonHeight);
        deleteUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!usersList.isSelectionEmpty()) {
                    file = new File("data/"+usersList.getSelectedValue()+".data");
                    if(file.delete())
                        JOptionPane.showMessageDialog(null, "کاربر با موفقیت حذف شد", "حذف کاربر", JOptionPane.WARNING_MESSAGE);
                    System.out.println(usersList.getSelectedValue());
                    usersNames.remove(usersList.getSelectedIndex());
                    loadNamesToJList();
                }
            }
        });
        buttonsPanel.add(deleteUserButton);
        addUserButton = new JButton("افزودن کاربر");
        addUserButton.setBounds(deleteUserButton.getX() + deleteUserButton.getWidth() + margin, buttonHeight, buttonWidth, buttonHeight);
        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rep = JOptionPane.showInputDialog(null, "نام کاربری خود را وارد کنید", "کاربر جدید", JOptionPane.INFORMATION_MESSAGE);
                if (usersNames.contains(rep))
                    JOptionPane.showMessageDialog(null, "این کاربر قبلا وجود دارد", "کاربر تکراری", JOptionPane.WARNING_MESSAGE);
                else if (rep != null) {
                    usersNames.add(rep);
                    loadNamesToJList();
                }
            }
        });
        buttonsPanel.add(addUserButton);

    }

    private void exitForm(WindowEvent e) {
        JSONObject names = new JSONObject();
        names.put("Names", usersNames);
        PrintWriter pw = null;
        PrintWriter writer = null;
        try {
            pw = new PrintWriter("data/game.data");
//            for (int i=0; i<fruitBasket.size(); i++) {
//                System.out.println(fruitBasket.get(i));
//            }
            writer = new PrintWriter("data/" + usersNames +".data");
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        pw.write(names.toJSONString());
        writer.write("{\"Level\":0}");
        writer.flush();
        writer.close();
        pw.flush();
        pw.close();
        System.exit(0);
    }
}
