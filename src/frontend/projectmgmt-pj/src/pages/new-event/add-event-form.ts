import { Moment } from "moment";

export type AddEventForm = {
  eventName: string,
  content: string,
  startTime: Moment,
  endTime: Moment,
  addressName: string,
  addressPx: number,
  addressPy: number,
  creditLimit: number,
  upperLimit: number,
  lowerLimit: number,
  tags: number[],
}
