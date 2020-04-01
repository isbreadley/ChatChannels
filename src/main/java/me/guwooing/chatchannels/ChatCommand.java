package me.guwooing.chatchannels;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatCommand implements CommandExecutor {

    private ChatChannels plugin;

    public ChatCommand(ChatChannels plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players may use that command.");
            return true;
        }

        Player player = ((Player) sender);

        if (args.length != 1) {
            player.sendMessage(Utils.chat("&cUsage: /chat <message>"));
            return true;
        }

        Channel channel = Channel.getChannel(args[0]);
        if (channel == null) {
            player.sendMessage(Utils.chat("&cYou provided an invalid channel name: " + args[0]));
            return true;
        }

        if (!channel.getPermission().equals("")) {
            if (!player.hasPermission(channel.getPermission())) {
                player.sendMessage(Utils.chat("You need to permission &7(" + channel.getPermission() + ") &cto use that channel."));
                return true;
            }
        }

        plugin.getChannelManager().setChannel(player.getUniqueId(), channel);
        player.sendMessage(Utils.chat("&aSet your chat channel to &e" + channel));
        return true;
    }
}
