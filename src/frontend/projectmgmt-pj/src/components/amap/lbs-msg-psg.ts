/**
 * Created by sunlu on 2018/11/1.
 */
import { Poi } from "./poi";

export class LBSMsgPsg {
  status: number;
  count: number;
  info: string;
  infocode: number;
  pois: Array<Poi>;
}
