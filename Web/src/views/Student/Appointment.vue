<template>
    <div id="appointment">
        <vue-cal
            ref="vuecal"
            selected-date="2018-11-19"
            :time-from="6 * 60"
            :time-to="23 * 60"
            :disable-views="['years']"
            editable-events
            hide-weekends
            events-on-month-view="short"
            events-count-on-year-view
            :min-event-width="minEventWidth"
            :events="events">
        </vue-cal>
    </div>
</template>

<script>
import VueCal from 'vue-cal'
import 'vue-cal/dist/vuecal.css'

export default {
    name: 'app',
    components: {
        VueCal
    },
    data: () => ({
        minEventWidth: 0,
        events: [
            {
                start: '2018-11-21 14:00',
                end: '2018-11-21 22:00',
                title: 'A big thing',
                content: '<i class="icon material-icons">sentiment_satisfied_alt</i>',
                class: 'health'
            },
            {
                start: '2018-11-21 16:00',
                end: '2018-11-21 19:00',
                title: 'Another thing',
                content: '<i class="icon material-icons">thumb_up</i>',
                class: 'blue-event'
            },
            {
                start: '2018-11-20 18:30',
                end: '2018-11-20 20:30',
                title: 'Crossfit',
                content: '<i class="icon material-icons">fitness_center</i>',
                class: 'sport'
            },
        ]
    }),
    methods: {
        addEvent(event) {
            this.events.push(event)
        },
        updateEvent(event) {
            const index = this.events.findIndex(e => e.id === event.id)
            this.events[index] = event
        },
        deleteEvent(event) {
            const index = this.events.findIndex(e => e.id === event.id)
            this.events.splice(index, 1)
        }
    }
}
</script>

<style>
/* Định dạng lại màu sắc cho Vue-Cal */
/* Thay đổi màu sắc chính */
.vuecal__menu,
.vuecal__cell-events-count {
    background-color: var(--color-white); /* Màu chính */
}

/* Thay đổi màu của thanh tiêu đề */
.vuecal__title-bar {
    background-color: #4880FF; /* Màu chính */
}

/* Thay đổi màu sắc của ngày hôm nay và ngày hiện tại */
.vuecal__cell--today,
.vuecal__cell--current {
    background-color: rgba(240, 240, 255, 0.4);
}

/* Thay đổi màu của ô được chọn */
.vuecal:not(.vuecal--day-view) .vuecal__cell--selected {
    background-color: rgba(235, 255, 245, 0.4);
}

/* Thay đổi màu của đường viền khi ô được chọn */
.vuecal__cell--selected:before {
    border-color: rgba(66, 185, 131, 0.5);
}

/* Thay đổi màu khi kéo sự kiện qua ô */
.vuecal__cell--highlighted:not(.vuecal__cell--has-splits),
.vuecal__cell-split--highlighted {
    background-color: rgba(195, 255, 225, 0.5);
}

/* Thay đổi màu của các nút và mũi tên khi được nhấn */
.vuecal__arrow.vuecal__arrow--highlighted,
.vuecal__view-btn.vuecal__view-btn--highlighted {
    background-color: rgba(136, 236, 191, 0.25);
}

.vuecal--month-view .vuecal__cell {height: 80px;}

.vuecal--month-view .vuecal__cell-content {
    justify-content: flex-start;
    height: 100%;
    align-items: flex-end;
}

.vuecal--month-view .vuecal__cell-date {padding: 4px;}
.vuecal--month-view .vuecal__no-event {display: none;}


.vuecal__cell-events-count {
    width: 18px;
    height: 2px;
    color: transparent;
}

/* Dot indicator */
.vuecal__cell-events-count {
    width: 4px;
    min-width: 0;
    height: 4px;
    padding: 0;
    color: transparent;
}

/* Cell background indicator */
.vuecal__cell--has-events {background-color: #fffacd;}
.vuecal__cell-events-count {display: none;}

</style>
