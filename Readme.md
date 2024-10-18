# Trade Application

## Overview
The Trade Application is a Java-based system designed to determine the best days to buy and sell gold, given a set of randomly generated prices over a period of time. The main objective of this application is to maximize profit or minimize loss when trading gold. The application uses an `ApiCaller` class to simulate price data, and the core logic for buying and selling is handled by the `TradeService` class.

## Project Structure
- **Main Class (`Main`)**: The entry point of the application that initializes the trading system and prints the trade details.
- **API Layer (`TradeApiCaller`)**: Responsible for generating and providing access to price data for each trading day.
- **Service Layer (`TradeService`)**: Contains the logic to determine the optimal day for buying and selling.
- **Interface (`TradeServiceInterface`)**: Defines the contract for the trading service.

## How It Works
1. The `TradeApiCaller` generates multiple lists of random prices representing the price of gold over several days.
2. The `TradeService` uses these generated prices to determine the best day to buy and sell based on the following conditions:
    - Buy at the lowest possible price.
    - Sell at the day with the highest profit after buying.
    - If prices are continually falling, the application will not make a perches. In This case I assume the best way is not to make any profit then minimize the loss.
3. The `Main` class runs the program, prints all generated prices, the selected buy day, and the sell day, providing a summary of the decision.

## Key Classes and Methods
### `Main`
- **`main(String[] args)`**: The entry point that initializes the `TradeApiCaller` and `TradeService`, finds the buy and sell days, and prints the trade details.

### `TradeApiCaller`
- **`TradeApiCaller()`**: Constructor that initializes random price lists.
- **`generatePriceLists()`**: Generates multiple lists with random prices.
- **`selectRandomPriceList()`**: Selects one of the generated price lists for analysis.
- **`getNumberOfDays()`**: Returns the number of days (size of the current price list).
- **`getPriceOnDay(int day)`**: Returns the price for a specific day.
- **`printTradeDetails(int buyDay, int sellDay)`**: Prints all generated prices, as well as the prices on the selected buy and sell days.

### `TradeService`
- **`getBuyDay()`**: Finds the day with the optimal day price for buying.
- **`getSellDay()`**: Finds the optimal day to sell to either maximize profit or minimize loss.

## Running the Application
1. Compile the Java files and run the `Main` class.
2. The program will output:
    - A list of all generated prices.
    - The selected buy day and price.
    - The selected sell day and price.

## Example Output

IMPORTANT: Note that output is countig days from 1,&#x20;

```
Prices: [5, 3, 8, 7, 10, 6]
Buy day (2): 3
Sell day (4): 10
Profit: 7
```

This output represents the decision to buy on day 2 (price 3) and sell on day 5 (price 10), with a profit of 7, aiming to maximize returns. 2. The program will output:

- A list of all generated prices.
- The selected buy day and price.
- The selected sell day and price.

## Example Output for not possible profit scenario

```
Prices: [10, 9, 8, 7, 6, 5]
Buy day: There was no day to sell gold. Profit is not possible
There is no gold to sell.
```

This output represents the decision not to buy because prices are constantly decreasing and no profit is possible.

## Key Considerations

- The application is designed to select the optimal buy and sell days to achieve the highest possible profit. In scenarios where prices are continually declining, the application will decide not to make a purchase to avoid unnecessary losses.
- The `TradeApiCaller` is responsible for generating random prices, and thus the results may vary each time the program is run.
- If the best day to buy turns out to be the last day in the series, the algorithm will adjust to avoid buying on that day since it would prevent any opportunity to sell and make a profit.&#x20;
- The `TradeApiCaller` is responsible for generating random prices, and thus the results may vary each time the program is run.

