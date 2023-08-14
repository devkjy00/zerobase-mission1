package com.jy.wifi;

import com.jy.wifi.Wifi;
import java.util.List;
import java.util.Map;
import lombok.Getter;

@Getter
public class WifiInfoDto {

    int list_total_count;
    Map RESULT;
    List<Wifi> row;
}
