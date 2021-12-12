package pw.yumc.MiaoLog4j2Fix;

import lombok.SneakyThrows;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.plugin.Plugin;

/**
 * Created with IntelliJ IDEA
 *
 * @author 喵♂呜
 * Created on 2017/10/25 20:35.
 */
@Plugin(id = "miaolog4j2fix", name = "MiaoLog4j2Fix", version = "0.0.1", authors = "MiaoWoo")
public class MiaoLog4j2FixSponge {
    @Listener
    @SneakyThrows
    public void onStarting(GameStartingServerEvent event) {
        new MiaoLog4j2Fix(Sponge.getServer());
    }
}
