import { Component, OnInit } from '@angular/core';
import {Channel} from "../../models/channel";
import {ChannelService} from "../../services/channel.service";
import {Chart} from 'chart.js';

@Component({
  selector: 'app-user-channel',
  templateUrl: './user-channel.component.html',
  styleUrls: ['./user-channel.component.css']
})
export class UserChannelComponent implements OnInit {

    public channel: Channel;
    public categories: string[];

    private chartData = [];
    private chartLabels = [];
    private chart: Chart;

    constructor(private channelService: ChannelService) { }

    ngOnInit() {
        const user = JSON.parse(localStorage.getItem('currentUser'));
        console.log(user.id);
        this.channelService.getChannelByUserId(user.id).subscribe(channel => {
            console.log(channel.displayId.length);
            this.channelService.getChannel(channel.displayId).subscribe(chan => {
                this.channel = chan;

            }, error => {
                console.log(error);
            });


            // get channel statistics
            this.channelService.getStatistics(channel.displayId).subscribe(result => {
                this.updateChart(result);
            });
        });

        this.channelService.getCategories().subscribe(categories => {
            this.categories = categories;
        });
        // console.log(this.channelService.getChannelByUserId(user.id));

        window.onload = (ev: Event) => {
            this.initCanvas();
        };
        // this.initCanvas();
    }

    updateChart(source: any) {
        this.chartData.splice(0);
        this.chartLabels.splice(0);

        for (let key in source) {
            this.chartLabels.push(key);
            this.chartData.push(source[key]);
        }

        if (this.chart != null) {
            this.chart.update();
        }
    }

    initCanvas() {
        const canvas: any = document.getElementById('myChart');
        console.log('Chart data:' + this.chartData);
        let chartData = [];
        // const ctx = canvas.getContext('2d');
        this.chart = new Chart(canvas, {
            type: 'doughnut',
            data: {
                labels: this.chartLabels,
                datasets: [
                    {
                        // label: "Population (millions)",
                        backgroundColor: ["#3e95cd", "#8e5ea2","#3cba9f","#e8c3b9","#c45850"],
                        data: this.chartData
                    }
                ]
            },
            options: {
                title: {
                    display: true,
                    text: 'Channel visitors'
                },
                // responsive: false
            }
        });
    }

    logChange() {
        console.log(this.channel.category);
    }

    saveChanges() {
        this.channelService.saveChannel(this.channel).subscribe(channel => {
            console.log(channel);
        });
    }

    toggleChannelActive() {
        this.channel.active = !this.channel.active;
    }

}
