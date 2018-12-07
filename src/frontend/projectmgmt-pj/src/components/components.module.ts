import { NgModule } from '@angular/core';
import { AMapComponent } from './amap/amap';
import { IonicModule } from "ionic-angular";
@NgModule({
	declarations: [AMapComponent],
	imports: [IonicModule,],
	exports: [AMapComponent]
})
export class ComponentsModule {}
