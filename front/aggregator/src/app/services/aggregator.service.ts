import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";


export interface Customer {
  customerId?: string
  firstName: string
  lastName: string
  phoneNumber: string
  email: string
  monthlyIncome: number
  monthlyExpenses: number
  marriedStatus: string
  dependents?: number
  agreeToBeScored: boolean
  amount: number,
  responseList?: Response[]
}

export interface Response {
  bank: string,
  status: string,
  offer: Offer
}

export interface Offer {
  monthlyPaymentAmount: number,
  totalRepaymentAmount: number,
  numberOfPayments: number,
  annualPercentageRate: number,
  firstRepaymentDate: Date
}

@Injectable({providedIn: 'root'})
export class AggregatorService {
  private url: string = 'http://localhost:8080/api/customers'
  public customer?: Customer

  constructor(private http: HttpClient) {
  }

  save(customer: Customer): void {
    this.http.post<any>(this.url, customer).subscribe(data => {
      console.log(data.customerId)
      this.customer = data
      this.update(this.customer?.customerId)
    })
  }

  update(customerId: string | undefined) {
    if (customerId !== undefined) {
      this.http.get<any>(this.url + "/" + customerId).subscribe(data => {
        this.customer = data
      })
    }
  }
}
