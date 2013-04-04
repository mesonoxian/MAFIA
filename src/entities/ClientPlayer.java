package entities;

import channels.SocketChannel;
import channels.SocketChannelListener;
import channels.messages.ChannelMessage;
import controllers.client.ClientEngine;
import gameMessages.*;

import java.io.IOException;

/*
  Job: Understands the player who is playing the game.
 */
public class ClientPlayer implements SocketChannelListener {
    private final String serverName;
    private final String playerName;
    private SocketChannel channel;
    private ClientEngine engine;

    public ClientPlayer(SocketChannel channel, String serverName, String playerName) {
        this.channel = channel;
        this.serverName = serverName;
        this.playerName = playerName;
        channel.bind(this);
    }

    @Override
    public void onClose(SocketChannel channel, Exception e) {
        System.out.println(playerName + " server closed failed");

        engine.serverClosed();
    }

    @Override
    public void onSendFailed(SocketChannel channel, IOException e, ChannelMessage message) {
        System.out.println(playerName + " sending failed");
        e.printStackTrace();
    }

    @Override
    public void onNewMessageArrived(SocketChannel channel, ChannelMessage message) {
        if (message instanceof PlayersConnectedMessage) {
            PlayersConnectedMessage pCm = (PlayersConnectedMessage) message;
            engine.displayConnectedPlayers(pCm.getPlayersConnected());
        }

        if (message instanceof VillagerRoleAssigned)
            engine.startVillagerScreen();

        if (message instanceof MafiaRoleAssigned)
            engine.startMafiaScreen();

        if (message instanceof NightArrivedMessage)
            engine.displayMafiaVotingChart(((NightArrivedMessage) message).getPlayerNames());

        if (message instanceof DayArrivedMessage)
            engine.displayVillagerVotingChart(((DayArrivedMessage) message).getPlayerNames());

        if (message instanceof KilledMessage)
            engine.showDeadScreen();

        if (message instanceof KilledPlayerMessage) {
            engine.PlayerKilled(((KilledPlayerMessage) message).getPlayerName());
        }

    }


    @Override
    public void onMessageReadError(SocketChannel channel, Exception e) {
        System.out.println(playerName + " message read failed");

        e.printStackTrace();
    }

    public void stop() {
        channel.stop();
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getServerName() {
        return serverName;
    }

    public void sendMessage(ChannelMessage message) {
        this.channel.send(message);
    }

    public void bindClientEngine(ClientEngine engine) {
        this.engine = engine;
    }
}
