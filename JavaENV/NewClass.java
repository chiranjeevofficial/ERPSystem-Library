JTabbedPane tabbedPane = new JTabbedPane();

JPanel panel1 = new JPanel();
panel1.add(new JLabel("This is the content of tab 1"));
tabbedPane.addTab("Tab 1", panel1);

JPanel panel2 = new JPanel();
panel2.add(new JLabel("This is the content of tab 2"));
tabbedPane.addTab("Tab 2", panel2);

// Add the tabbed pane to a container and display it
JFrame frame = new JFrame();
frame.add(tabbedPane);
frame.pack();
frame.setVisible(true);
