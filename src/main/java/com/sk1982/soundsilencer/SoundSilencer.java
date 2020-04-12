package com.sk1982.soundsilencer;

import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.slf4j.Logger;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.effect.sound.SoundType;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.event.sound.PlaySoundEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.io.IOException;

@Plugin(
        id = "soundsilencer",
        name = "Sound Silencer",
        description = "Silences global broadcast sounds",
        authors = {
                "SK1982"
        }
)
public class SoundSilencer {

    private Config config;

    @Inject
    @DefaultConfig(sharedRoot = true)
    private ConfigurationLoader<CommentedConfigurationNode> configManager;

    @Inject
    private Logger logger;

    @Listener
    public void onReload(GameReloadEvent event) {
        try {
            config = loadConfig();
        } catch (IOException e) {
            logger.error("config load failed! " + e.getMessage());
        }
    }

    @Listener
    public void onInit(GameInitializationEvent event) {
        try {
            config = loadConfig();
        } catch (IOException e) {
            logger.error("config load failed! loading defaults " + e.getMessage());
            config = new Config();
        }
    }

    @Listener
    public void onServerStopping(GameStoppingServerEvent event) {
        try {
            saveConfig(config);
        } catch (IOException e) {
            logger.error("config save failed! " + e.getMessage());
        }
    }

    @Listener
    public void onSoundBroadcast(PlaySoundEvent.Broadcast event) {
        SoundType sound = event.getSoundType();
        String id = sound.getId();

        if (shouldCancelSound(id)) {
            event.setCancelled(true);

            if (config.convertToLocal) {
                Float volume = config.volumeMap.getOrDefault(id, config.defaultVolume);
                if (volume < 0) volume = event.getVolume();
                Location<World> location = event.getLocation();
                location.getExtent().playSound(
                        sound,
                        event.getSoundCategory(),
                        location.getPosition(),
                        volume,
                        event.getPitch()
                );
            }

            if (config.logCancels) {
                logger.info("Sound broadcast " + id + " was canceled");
            }
        } else if (config.logNonCancels) {
            logger.info("Sound broadcast " + id + " was NOT canceled");
        }
    }

    private boolean shouldCancelSound(String soundId) {
        return config.muteList.contains(soundId) ^ config.whitelist;
    }

    private CommentedConfigurationNode getRootNode() throws IOException {
        return configManager.load();
    }

    private Config loadConfig() throws IOException {
        Config config = null;
        try {
            config = getRootNode().getValue(TypeToken.of(Config.class));
        } catch (ObjectMappingException e) {
            logger.error("config file is corrupt! loading default config");
        }

        if (config == null) {
            config = new Config();
            saveConfig(config);
        }

        return config;
    }

    private void saveConfig(Config config) throws IOException {
        try {
            configManager.save(getRootNode().setValue(TypeToken.of(Config.class), config));
        } catch (ObjectMappingException e) {
            logger.error("error in writing to config! " + e.getMessage());
        }
    }
}
