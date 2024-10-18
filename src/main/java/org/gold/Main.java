package org.gold;

import org.gold.trade.TradeService;
import org.gold.trade.TradeServiceInterface;

public class Main {

  public static void main(String[] args) {
    TradeApiCaller tradeApiCaller = new TradeApiCaller();

    TradeServiceInterface tradeServiceInterface = new TradeService(tradeApiCaller);

    int buyDay = tradeServiceInterface.getBuyDay();
    int sellDay = tradeServiceInterface.getSellDay();

    tradeApiCaller.printTradeDetails(buyDay, sellDay);

  }
}