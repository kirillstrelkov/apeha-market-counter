package apeha.market;

import java.util.List;

public interface Transmissible {
    List<Item> getList();

    void updateTable(List<Item> list);
}
