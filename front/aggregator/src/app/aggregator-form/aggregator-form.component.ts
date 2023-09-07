import {Component, OnInit} from "@angular/core";
import {AggregatorService, Customer, Offer} from "../services/aggregator.service";

@Component({
  selector: 'app-aggregator-form',
  templateUrl: './aggregator-form.component.html',
  styleUrls: ['./aggregator-form.component.scss']
})
export class AggregatorFormComponent implements OnInit {

  firstName: string = ''
  lastName: string = ''
  phoneNumber: string = ''
  email: string = ''
  monthlyIncome: number = 0
  monthlyExpenses: number = 0
  marriedStatus: string = ''
  dependents: number = 0
  agreeToBeScored: boolean = false
  amount: number = 0


  constructor(public aggregatorService: AggregatorService) {
  }

  ngOnInit(): void {
  }

  save() {
    const customer: Customer = {
      firstName: this.firstName,
      lastName: this.lastName,
      phoneNumber: this.phoneNumber,
      email: this.email,
      monthlyIncome: this.monthlyIncome,
      monthlyExpenses: this.monthlyExpenses,
      marriedStatus: this.marriedStatus,
      dependents: this.dependents,
      agreeToBeScored: this.agreeToBeScored,
      amount: this.amount
    }
    this.aggregatorService.save(customer);
    this.clean();
  }

  update() {
    this.aggregatorService.update(this.aggregatorService.customer?.customerId)
  }

  clean() {
    this.firstName = ''
    this.lastName = ''
    this.phoneNumber = ''
    this.email = ''
    this.monthlyIncome = 0
    this.monthlyExpenses = 0
    this.marriedStatus = ''
    this.dependents = 0
    this.agreeToBeScored = false
    this.amount = 0
  }

  convertOffer(offer: Offer): String {
    if (offer !== null && offer !== undefined) {
      return "monthly payment amount: " + offer.monthlyPaymentAmount + "\n" +
        "total repayment amount: " + offer.totalRepaymentAmount + "\n" +
        "number of payments: " + offer.numberOfPayments + "\n" +
        "annual percentage rate: " + offer.annualPercentageRate + "\n" +
        "first repayment date: " + offer.firstRepaymentDate + "\n";
    }
    return "";
  }
}
