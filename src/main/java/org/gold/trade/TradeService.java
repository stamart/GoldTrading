package org.gold.trade;

import java.util.Comparator;
import java.util.stream.IntStream;
import org.gold.TradeApiCaller;

public class TradeService implements TradeServiceInterface {

  TradeApiCaller api;

  /**
   * Constructor for the Solution class, initializes the API caller.
   *
   * @param api an instance of ApiCaller used to get price data
   */
  public TradeService(TradeApiCaller api) {
    this.api = api;
  }

  /**
   * Finds the optimal day to sell to either maximize profit or minimize loss. If no profit is
   * possible, it will not perform a purchase or sale.
   *
   * @return the day (index) with the lowest price, or -1 if no prices are available
   */
  @Override
  public int getBuyDay() {
    int numberOfDays = api.getNumberOfDays();
    if (numberOfDays <= 1 || noProfitPossible()) {
      return -1;
    }

    int maxProfit = Integer.MIN_VALUE;
    int bestBuyDay = -1;

    for (int buyDay = 0; buyDay < numberOfDays - 1; buyDay++) {
      int minPrice = api.getPriceOnDay(buyDay);

      int bestSellDayProfit = getBestProfitOnDay(numberOfDays, buyDay, minPrice);

      if (bestSellDayProfit > maxProfit) {
        maxProfit = bestSellDayProfit;
        bestBuyDay = buyDay;
      }
    }
    return bestBuyDay;
  }

  /**
   * Finds the best day to sell silver after buying it to maximize profit or minimize loss.
   *
   * @return the day (index) to sell for maximum profit, or the next day if no profit can be made
   */
  @Override
  public int getSellDay() {
    int buyDay = getBuyDay();
    int numbersOfDays = api.getNumberOfDays();

    if (numbersOfDays <= 1 || buyDay == -1) {
      return -1; /** No possibility to sell*/
    }

    /** Maximizing profit or minimizing loss*/
    return IntStream
        .range(buyDay + 1, numbersOfDays) /** Browse days after the buy day*/
        .boxed() /** Cast primitive int to Integer*/
        .max(Comparator.comparingInt(
            day -> api.getPriceOnDay(day) - api.getPriceOnDay(buyDay))) /** Maximize profit*/
        .orElse(-1);/** If no better day is found, sell the next day*/
  }

  /**
   * Helper method used to determine the maximum profit for a specific buy day. It iterates through
   * subsequent days and calculates the profit by comparing the buy day price with each sell day
   * price.
   */
  private int getBestProfitOnDay(int numberOfDays, int buyDay, int minPrice) {
    int bestSellDay = IntStream.range(buyDay + 1, numberOfDays)
        .map(sellDay -> api.getPriceOnDay(sellDay) - minPrice)
        .max()
        .orElse(Integer.MIN_VALUE);
    return bestSellDay;
  }

  /**
   * This method determinate case where prices constantly are decreasing, In that case the best
   * possible way to minimize loss is not to buy
   */
  private boolean noProfitPossible() {
    boolean alwaysDecreasing = true;
    for (int i = 1; i < api.getNumberOfDays(); i++) {
      if (api.getPriceOnDay(i) >= api.getPriceOnDay(i - 1)) {
        alwaysDecreasing = false;
        break;
      }
    }
    return alwaysDecreasing;
  }
}
