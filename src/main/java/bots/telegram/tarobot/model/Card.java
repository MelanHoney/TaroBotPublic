package bots.telegram.tarobot.model;

import lombok.Builder;

import java.io.File;

@Builder
public record Card(String name, File image) {
}
