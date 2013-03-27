package screens.server;

import controllers.server.Player;
import controllers.server.PlayersRoleInfoController;
import screens.controls.IMainFrame;
import screens.controls.ImagePanel;
import views.server.PlayersRoleInfoView;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PlayersRoleInfoScreen implements PlayersRoleInfoView {

    private static final String BG_IMAGE = "images/playersConnectedScreen.jpg";
    private final IMainFrame mainFrame;
    private final PlayersRoleInfoController controller;
    private ImagePanel panel;
    private JLabel playerName;
    private JLabel role;
    private DefaultListModel<String> playersNameList = new DefaultListModel<String>();
    private JList playersName = new JList(playersNameList);
    private DefaultListModel<String> playersRoleList = new DefaultListModel<String>();
    private JList playersRole = new JList(playersRoleList);


    public PlayersRoleInfoScreen(IMainFrame mainFrame, PlayersRoleInfoController controller) {
        this.mainFrame = mainFrame;
        this.controller = controller;
        panel = mainFrame.createImagePanel(BG_IMAGE);
        playersName = createList(playersName, 50, 100);
        playersRole = createList(playersRole, 300, 100);
        panel.add(playersName);
        panel.add(playersRole);
    }

    private JList createList(JList playersList, int x_bound, int y_bound) {
        playersList.setSize(200, 850);
        playersList.setBorder(BorderFactory.createLineBorder(SystemColor.YELLOW));
        playersList.setLocation(x_bound, y_bound);
        playersList.setBackground(Color.YELLOW);
        Font f = new Font("Monospaced", Font.PLAIN, 20);
        playersList.setFont(f);
        return playersList;
    }

    @Override
    public void display(List<Player> players) {
        for (Player player : players) {
            playersNameList.addElement(player.getName());
            playersRoleList.addElement(player.getRole());
        }
    }

}
