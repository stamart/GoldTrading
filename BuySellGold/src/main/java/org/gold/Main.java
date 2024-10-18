package org.gold;

import org.gold.trade.TradeService;

public class Main {

  public static void main(String[] args) {
    TradeApiCaller tradeApiCaller = new TradeApiCaller();
    TradeService tradeService = new TradeService(tradeApiCaller);

    int buyDay = tradeService.getBuyDay();
    int sellDay = tradeService.getSellDay();

    tradeApiCaller.printTradeDetails(buyDay, sellDay);

  }
}