import { NgModule } from '@angular/core';
import { StepperComponent } from './stepper/stepper';
import { AMapComponent } from './amap/amap';
import { IonicModule } from "ionic-angular";
@NgModule({
	declarations: [StepperComponent, AMapComponent],
	imports: [IonicModule,],
	exports: [StepperComponent, AMapComponent,]
})
export class ComponentsModule {}
