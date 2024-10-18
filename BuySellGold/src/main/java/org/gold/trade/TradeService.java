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
   * Finds the best day to buy silver by looking for the minimum price.
   *
   * @return the day (index) with the lowest price, or -1 if no prices are available
   */
  @Override
  public int getBuyDay() {
    Integer integer = IntStream.range(0, api.getNumberOfDays()) /** Browse day indices */
        .boxed()
        .min(Comparator.comparingInt(api::getPriceOnDay)) /** Find the day with the lowest price*/
        .orElse(-1); /** Return -1 if the price list is empty*/
        return integer;
  }

  /**
   * Finds the best day to sell silver after buying it to maximize profit or minimize loss.
   *
   * @return the day (index) to sell for maximum profit, or the next day if no profit can be made
   */
  @Override
  public int getSellDay() {
    int buyDay = getBuyDay();
    int liczbaDni = api.getNumberOfDays();

    if (liczbaDni <= 1) {
      return -1; /** No possibility to sell*/
    }

    /** Maximizing profit or minimizing loss*/
    return IntStream
        .range(buyDay + 1, liczbaDni) /** Browse days after the buy day*/
        .boxed() /** Cast primitive int to Integer*/
        .max(Comparator.comparingInt(
            day -> api.getPriceOnDay(day) - api.getPriceOnDay(buyDay))) /** Maximize profit*/
        .orElse(- 1);/** If no better day is found, sell the next day*/

  }

}
