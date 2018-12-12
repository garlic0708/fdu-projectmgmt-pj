import { NgModule } from '@angular/core';
import { StepperComponent } from './stepper/stepper';
import { AMapComponent } from './amap/amap';
import { IonicModule } from "ionic-angular";
import { ImgWithRequestComponent } from './img-with-request/img-with-request';
import { IonicImageLoader } from "ionic-image-loader";
import { EventSummaryComponent } from './event-summary/event-summary';
import { PipesModule } from "../pipes/pipes.module";

@NgModule({
  declarations: [
    StepperComponent,
    AMapComponent,
    ImgWithRequestComponent,
    EventSummaryComponent
  ],
  imports: [
    IonicImageLoader,
    IonicModule,
    PipesModule,
  ],
  exports: [
    StepperComponent,
    AMapComponent,
    ImgWithRequestComponent,
    EventSummaryComponent,
  ]
})
export class ComponentsModule {
}
