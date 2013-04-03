package controllers.server;

import channels.SocketChannel;
import controllers.Workflow;
import entities.Player;
import entities.Players;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import views.server.ConnectedPlayersView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Job :- Ensures the correctness of behaviour of ConnectedPlayersController.
 */
public class ConnectedPlayersControllerTest {
    private ConnectedPlayersView view;
    private Workflow workflow;
    private Players players;
    private Player playerOne;
    private ConnectedPlayersController controller;

    @Before
    public void setUp() throws Exception {
        players = mock(Players.class);
        workflow = mock(Workflow.class);
        view = mock(ConnectedPlayersView.class);
        playerOne = new Player(mock(SocketChannel.class), mock(GameEngine.class));
        players.addPlayer(playerOne);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void when_assign_Role_is_called_the_Players_assign_role_is_invoke() {
        controller = new ConnectedPlayersController(players, workflow);
        controller.assignRoles();
        verify(players).assignRoles();
    }

    @Test
    public void when_display_is_called_the_view_display_is_invoke() {
        controller = new ConnectedPlayersController(players, workflow);
        controller.bind(view);
        controller.display();
        verify(view).display(players.getPlayers());
    }


    @Test
    public void Start_Invokes_assignRoles_Which_Inturn_Invokes_Player_assignRole() {
        controller = new ConnectedPlayersController(players, workflow);
        controller.start();
        verify(players).assignRoles();
    }


}
