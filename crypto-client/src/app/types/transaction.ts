import { TransactionType } from "./transactionType";

export interface Transaction {
    id: number,
    userId: number,
    dateProduced: Date,
    symbol: string,
    pricePerShare: number,
    amountOfShares: number,
    action: TransactionType
}