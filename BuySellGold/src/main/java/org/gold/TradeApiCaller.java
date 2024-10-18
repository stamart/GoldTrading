package org.gold;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TradeApiCaller {

  private final List<List<Integer>> priceLists;
  private List<Integer> currentPrices;
  private final Random random;

  /**
   * Constructor initializing random price lists
   */
  public TradeApiCaller() {
    this.random = new Random();
    this.priceLists = generatePriceLists();
    selectRandomPriceList();
  }

  /**
   * Method generating several lists with random prices
   */
  private List<List<Integer>> generatePriceLists() {
    List<List<Integer>> lists = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      List<Integer> list = new ArrayList<>();
      for (int j = 0; j < 10 + random.nextInt(10); j++) {
        list.add(1 + random.nextInt(20));
      }
      lists.add(list);
    }
    return lists;
  }

  /**
   * Method selecting a random list from available ones
   */
  public void selectRandomPriceList() {
    if (!priceLists.isEmpty()) {
      currentPrices = priceLists.get(random.nextInt(priceLists.size()));
    } else {
      throw new IllegalStateException("Price lists are empty");
    }
  }

  /**
   * Returns the number of days, which is the size of the current price list
   */
  public int getNumberOfDays() {
    if (currentPrices == null) {
      throw new IllegalStateException("No price list selected");
    }
    return currentPrices.size();
  }

  /**
   * Returns the price of gold for the given day
   */
  public int getPriceOnDay(int day) {
    if (currentPrices == null) {
      throw new IllegalStateException("No price list selected");
    }
    if (day < 0 || day >= currentPrices.size()) {
      throw new IllegalArgumentException("Day out of range");
    }
    return currentPrices.get(day);
  }

  /**
   * Method printing all prices, as well as the price on buy and sell days
   */
  void printTradeDetails(int buyDay, int sellDay) {
    if (currentPrices == null) {
      throw new IllegalStateException("No price list selected");
    }
    System.out.println("Info:Buy day and Sell day are counted from 1");
    System.out.println("Prices: " + currentPrices);
    try {
      System.out.println("Buy day (" + (buyDay + 1) + "): " + getPriceOnDay(buyDay));
    } catch (IllegalArgumentException e) {
      System.out.println("Buy day: There was no day to sell gold. Profit is not possible");

    }
    try {
      System.out.println("Sell day (" + (sellDay + 1) + "): " + getPriceOnDay(sellDay));
      System.out.println("Profit: " + (getPriceOnDay(sellDay) - getPriceOnDay(buyDay)));

    } catch (IllegalArgumentException e) {
      System.out.println("There is no gold to sell. Sorry :(");
    }
  }

}
