import { NgModule } from '@angular/core';
import { StepperComponent } from './stepper/stepper';
import { AMapComponent } from './amap/amap';
import { IonicModule } from "ionic-angular";
import { ImgWithRequestComponent } from './img-with-request/img-with-request';
import { IonicImageLoader } from "ionic-image-loader";

@NgModule({
  declarations: [StepperComponent, AMapComponent,
    ImgWithRequestComponent],
  imports: [IonicImageLoader, IonicModule,],
  exports: [StepperComponent, AMapComponent,
    ImgWithRequestComponent,]
})
export class ComponentsModule {
}
