package cn.arros.server.entity.dto;

import java.util.Arrays;

/**
 * @author Zero
 * @date 2022/6/5 22:28
 * @description
 * @since 1.8
 **/
public class WebHookInfo {

    private String name = "web";

    private String[] events;

    private HookConfig hookConfig;

    private boolean active = true;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getEvents() {
        return events;
    }

    public void setEvents(String[] events) {
        this.events = events;
    }

    public HookConfig getHookConfig() {
        return hookConfig;
    }

    public void setHookConfig(HookConfig hookConfig) {
        this.hookConfig = hookConfig;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "WebHookInfo{" +
                "name='" + name + '\'' +
                ", events=" + Arrays.toString(events) +
                ", hookConfig=" + hookConfig +
                ", active=" + active +
                '}';
    }
}
