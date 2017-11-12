package org.karaoke.parser;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.karaoke.domain.Argument;
import org.karaoke.domain.Category;
import org.karaoke.domain.Karaoke;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.karaoke.domain.Category.SINGER;
import static org.karaoke.domain.Category.SONG;

@Component("TJ")
@Slf4j
public class TJParser extends Parser {

    private static final String URL = "https://www.tjmedia.co.kr/tjsong/song_search_list.asp?";
    private Map<Category, String> URLQuery;

    @PostConstruct
    public void setUp() {
        URLQuery = new HashMap<>();
        URLQuery.put(SINGER, "strType=2");
        URLQuery.put(SONG, "strType=1");
    }

    public List<Karaoke> parse(Argument argument, int page) throws IOException {
        StringBuilder str = new StringBuilder(URL);
        str.append(URLQuery.get(argument.getCategory()))
                .append("&strText=")
                .append(argument.getWord())
                .append("&strSize02=10");
        Document doc = Jsoup.connect(str.toString()).get();
        Elements elements = doc.select("table.board_type1 tr:has(td)");
        return buildKaraokeList(elements);
    }
}