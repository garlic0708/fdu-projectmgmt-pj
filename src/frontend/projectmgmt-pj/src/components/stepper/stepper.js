var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { TextInput } from "ionic-angular";
/**
 * Generated class for the StepperComponent component.
 *
 * See https://angular.io/api/core/Component for more info on Angular
 * Components.
 */
var StepperComponent = /** @class */ (function () {
    function StepperComponent() {
        this._value = 0;
        this.valueChange = new EventEmitter();
        this.enabled = true;
        this.maxValue = null;
        this.minValue = null;
        console.log('Hello StepperComponent Component');
    }
    StepperComponent.prototype.isLegal = function (v) {
        return (this.minValue === null || this.minValue <= v) &&
            (this.maxValue === null || this.maxValue >= v) &&
            v === parseInt(v);
    };
    Object.defineProperty(StepperComponent.prototype, "value", {
        get: function () {
            return this._value;
        },
        set: function (v) {
            if (!isNaN(v) && this.isLegal(+v)) {
                v = +v;
                if (this._value !== v) {
                    this._value = v;
                    this.valueChange.emit(v);
                }
            }
        },
        enumerable: true,
        configurable: true
    });
    StepperComponent.prototype.increment = function () {
        this.value += 1;
    };
    StepperComponent.prototype.decrement = function () {
        this.value -= 1;
    };
    StepperComponent.prototype.blur = function () {
        if (this.inputBox.value != this.value.toString()) {
            this.inputBox.setValue(this.value);
        }
    };
    __decorate([
        Output(),
        __metadata("design:type", Object)
    ], StepperComponent.prototype, "valueChange", void 0);
    __decorate([
        Input(),
        __metadata("design:type", Boolean)
    ], StepperComponent.prototype, "enabled", void 0);
    __decorate([
        Input(),
        __metadata("design:type", Number)
    ], StepperComponent.prototype, "maxValue", void 0);
    __decorate([
        Input(),
        __metadata("design:type", Number)
    ], StepperComponent.prototype, "minValue", void 0);
    __decorate([
        ViewChild(TextInput),
        __metadata("design:type", TextInput)
    ], StepperComponent.prototype, "inputBox", void 0);
    __decorate([
        Input(),
        __metadata("design:type", Object),
        __metadata("design:paramtypes", [Object])
    ], StepperComponent.prototype, "value", null);
    StepperComponent = __decorate([
        Component({
            selector: 'stepper',
            templateUrl: 'stepper.html'
        }),
        __metadata("design:paramtypes", [])
    ], StepperComponent);
    return StepperComponent;
}());
export { StepperComponent };
//# sourceMappingURL=stepper.js.map