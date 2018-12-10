import { Moment } from "moment";

export type AddEventForm = {
  eventName: string,
  content: string,
  startTime: Moment,
  endTime: Moment,
  poiId: string,
  addressName: string,
  addressLocation: string,
  addressPx: number,
  addressPy: number,
  creditLimit: number,
  upperLimit: number,
  lowerLimit: number,
  tags: number[],
}
