package pers.etherealss.demo.controller;

import pers.etherealss.demo.core.mvc.annotation.ServletHandler;
import pers.etherealss.demo.core.mvc.enums.RequestType;

/**
 * @author wtk
 * @description 这个Controller没啥用，写着玩的
 * @date 2021-08-27
 */
@ServletHandler("/musics")
public class MusicController {

    @ServletHandler(value = "getMusic", type = RequestType.GET)
    public String getMusic(int id) {
        return "getMusic";
    }

    @ServletHandler(value = "addMusic")
    public String addMusic(String name) {
        return "addMusic";
    }

}
