/**
 * Created by sunlu on 2018/11/1.
 */
export class Poi {
  id: string;
  name: string;
  district: string;
  adcode: number;
  location: string | { lng: number, lat: number };
  address: string;
  typecode: string;
  
  static getLnglat(item: Poi): number[] {
    let x: any;
    let y: any;
    if (typeof item.location === "string") {
      console.log(item.location.indexOf(","));
      x = item.location.substr(0, item.location.indexOf(","));
      y = item.location.substr(item.location.indexOf(",") + 1, item.location.length - item.location.indexOf(",") - 1);
    } else {
      x = item.location.lng;
      y = item.location.lat;
    }
    return [x, y];
  }
}
