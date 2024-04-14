<template>
    <div id="appointment" class="appointment-wrapper">
        <a-select
            ref="select"
            v-model:value="selected"
            style="width: 120px"
            @focus="focus"
            @change="handleChangeView"
            class="select-view"
        >
            <a-select-option value="day">Day</a-select-option>
            <a-select-option value="week">Week</a-select-option>
            <a-select-option value="month">Month</a-select-option>
        </a-select>

        <div class="calendar-container">
            <vue-cal
                ref="vuecal"
                small
                v-model:active-view="activeView"
                hide-view-selector
                :selected-date="currentDate"
                :time-from="6 * 60"
                :time-to="23 * 60"
                :watchRealTime="true"
                :disable-views="['years', 'year']"
                editable-events
                :hide-weekends="false"
                events-on-month-view="short"
                events-count-on-year-view
                :min-event-width="minEventWidth"
                :events="events"
                class="calendar-scrollable"
                @event-drag-create="openModal"
                @event-click="openModelInfo"
                @onEventDblclick="openModelInfo"
                :minCellWidth="100"
                @event-duration-change="handleDurationChange"
                @event-drop="handleEventDrop"
            >
                <template v-slot:controls="{ changeView }">
                    <div class="custom-buttons">
                        <button @click="changeView('day')">Day</button>
                        <button @click="changeView('week')">Week</button>
                        <button @click="changeView('month')">Month</button>
                    </div>
                </template>
            </vue-cal>
        </div>
        <a-modal v-model:open="modalVisible" @ok="handleOk" @cancel="cancelEvent" >
            <div class="form-model">
                <a-input :value="newEvent.title" @update:value="newValue => newEvent.title = newValue" id="title" placeholder="Add title" class="space" />

                <a-time-range-picker
                    :value="[newEvent.startTime, newEvent.endTime]"
                    @update:value="updateTimeRange"
                    id="timeRange"
                    class="space"
                    style="width: 50%"
                />
                <a-input-number :value="reminder" @update:value=" newValue => reminder= newValue" style="width: 45%; margin-left: 5%" class="space" placeholder="Reminder">
                    <template #addonAfter>
                        <a-select :value="typeTime" @update:value=" newValue => typeTime= newValue"
                                  style="width: 100px;">
                            <a-select-option value="Minutes">Minutes</a-select-option>
                            <a-select-option value="Hours">Hours</a-select-option>
                            <a-select-option value="Day">Day</a-select-option>
                        </a-select>
                    </template>
                </a-input-number>
                <a-input :value="newEvent.location" @update:value=" newValue => newEvent.location= newValue" id="location" placeholder="Add location" class="space"/>
                <a-select
                    v-model:value="guest"
                    mode="multiple"
                    style="width: 100%; max-height: 200px"
                    placeholder="Please select"
                    :options="listUser"
                    @change="handleChange"
                    :filter-option="(input, option) => option.label.toLowerCase().indexOf(input.toLowerCase()) >= 0"
                />
            </div>
        </a-modal>
        <a-modal v-model:open="modelInfoVisible" >
            <div class="form-model">
                <a-input :value="infoEvent.title" @update:value="newValue => infoEvent.title = newValue" id="title" placeholder="Add title" class="space" />
                <a-time-range-picker
                    :value="[infoEvent.startTime, infoEvent.endTime]"
                    id="timeRange"
                    class="space"
                    style="width: 50%"
                />

                <a-input-number :value="infoEvent.reminder" @update:value="newValue => infoEvent.reminder = newValue" style="width: 45%; margin-left: 5%" class="space" placeholder="Reminder">
                    <template #addonAfter>
                        <a-select :value="infoEvent.typeTime" @update:value="newValue => infoEvent.typeTime = newValue" style="width: 100px;">
                            <a-select-option value="Minutes">Minutes</a-select-option>
                            <a-select-option value="Hours">Hours</a-select-option>
                            <a-select-option value="Day">Day</a-select-option>
                        </a-select>
                    </template>
                </a-input-number>

                <a-input :value="infoEvent.location" @update:value="newValue => infoEvent.location = newValue" id="location" placeholder="Add location" class="space"/>

                <!-- Chọn khách mời -->
                <a-select
                    v-model:value="infoEvent.listGuest"
                    mode="multiple"
                    style="width: 100%; max-height: 200px"
                    placeholder="Please select"
                    :options="listUser"
                    @change="handleChange"
                    :filter-option="(input, option) => option.label.toLowerCase().indexOf(input.toLowerCase()) >= 0"
                />
            </div>
        </a-modal>
    </div>
</template>

<script setup>
import VueCal from 'vue-cal'
import 'vue-cal/dist/vuecal.css'
import {ref, watch} from 'vue'
import dayjs from "dayjs"
const reminder = ref()
const typeTime = ref('Minutes')
const modalVisible = ref(false)
const minEventWidth = ref(0)
const selected = ref('week')
const currentDate = ref('')
const listGuest = ref([])
const title = ref('')
const activeView = ref('week')
const modelInfoVisible = ref(false)
const newEvent = ref({
    title: '',
    startTime: dayjs(),
    endTime: dayjs(),
    location: '',
})
const infoEvent = ref({
    id: '',
    title: '',
    startTime: dayjs(),
    endTime: dayjs(),
    location: '',
    listGuest: [],
    reminder: '',
    typeTime: ''

})
const listUser = ref([
    {
        value: 1,
        label: 'Ngô Mậu Trường'
    },
    {
        value: 2,
        label: 'Phạm Thành Vinh'
    },
    {
        value: 3,
        label: 'Nguyễn Trương Anh Minh'
    }
])
const events = ref([
    {
        id: 1,
        start: '2024-04-13 14:00',
        end: '2024-04-13 22:00',
        title: 'Need to go shopping',
        location: 'Phòng E404',
        class: 'student',
        listGuest: ['Ngo Mau Truong'],
        reminder: '30',
        typeTime: 'Minutes'
    },
    {
        id: 2,
        start: '2024-04-12 16:00',
        end: '2024-04-12 19:00',
        title: 'Golf with John',
        location: 'Phòng F204',
        class: 'important',
        listGuest: ['Nguyen Ho Minh Quan', 'Tran Tan Thanh'],
        reminder: '1',
        typeTime: 'Hours'
    },
    {
        id: 3,
        start: '2024-04-11 08:00',
        end: '2024-04-11 20:00',
        title: 'Golf with John',
        location: 'Phòng H203',
        class: 'important',
        listGuest: ['Nguyen Ho Minh Quan', 'Tran Tan Thanh'],
        reminder: '2',
        typeTime: 'Hours'
    },
])

const handleChangeView = view => {
    activeView.value = view
}


const handleOk = () => {
    const nameValue = newEvent.value.title
    console.log('Name value:', nameValue)
    console.log('ENd: ', newEvent.value.endTime)
    console.log('Start: ', newEvent.value.startTime)
    console.log('ListGuest', listGuest.value)
    console.log('Reminder: ', reminder.value + ' ' + typeTime.value)

    modalVisible.value = false
}

const cancelEvent = () => {
    modalVisible.value = false
    window.location.reload()
}

const openModal = (event) => {
    modalVisible.value = true
    const startTime = event.start
    const endTime = event.end
    newEvent.value.startTime = dayjs(startTime)
    newEvent.value.endTime = dayjs(endTime)
}

const openModelInfo = (event) => {
    modelInfoVisible.value = true
    infoEvent.value.title = event.title
    infoEvent.value.id = event.id
    infoEvent.value.startTime = dayjs(event.start)
    infoEvent.value.endTime = dayjs(event.end)
    infoEvent.value.location = event.location
    infoEvent.value.listGuest = event.listGuest
    infoEvent.value.reminder = event.reminder
    infoEvent.value.typeTime = event.typeTime

    console.log('EVENT:', infoEvent);
}

const getCurrentDay = () => {
    return new Date().toLocaleDateString()
}
currentDate.value = getCurrentDay()

const handleChange = guest => {
    console.log('User: ', guest)
    listGuest.value.push(guest)
}

const updateTimeRange = (newValue) => {
    newEvent.value.startTime = newValue[0]
    newEvent.value.endTime = newValue[1]
}

const handleCancel = () => {
    open.value = false;
}

const handleDurationChange = (event) => {
    console.log('Event resized:', event)
}

const handleEventDrop = (event) => {
    console.log('Event drop:', event)
}

// Phương thức xử lý xóa sự kiện
const deleteEvent = () => {
    const eventId = infoEvent.value.id;
    // Thực hiện xóa sự kiện ở đây (ví dụ: thông qua API hoặc một hành động khác)
    // Sau khi xóa xong, đóng modal
    modalVisible.value = false;
}

// Phương thức xử lý cập nhật sự kiện
const updateEvent = () => {
    const eventId = infoEvent.value.id;
    const updatedEvent = {
        id: eventId,
        title: infoEvent.value.title,
        startTime: infoEvent.value.startTime,
        endTime: infoEvent.value.endTime,
        location: infoEvent.value.location,
        listGuest: infoEvent.value.listGuest,
        reminder: infoEvent.value.reminder
    }
    modalVisible.value = false;
}

</script>

<style>
.appointment-wrapper {
    padding: 10px;
    background-color: var(--color-white);
    border-radius: 10px;
    overflow: hidden;
}

.select-view {
    margin: 10px;
}

.calendar-container {
    overflow-y: auto;
    height: 86vh
}

.calendar-scrollable {
    overflow-x: hidden
}

.form-model {
    padding: 30px 10px;
}

#name {
    border: none;
    border-bottom: 2px var(--color-blue) solid;
    border-radius: inherit;
    font-size: 18px;
    font-weight: bold;
    color: var(--color-blue);
}

#name:focus-visible, #name:focus
{
    border-color: var(--color-white);
    border-bottom: 2px var(--color-blue) solid;
    box-shadow: none;
}

.space {
    margin-bottom: 20px;
    box-shadow: none;
}

.vuecal__menu,
.vuecal__cell-events-count {
    background-color: var(--color-white);
}

.vuecal__title-bar {
    background-color: var(--color-white);
    line-height: 60px;
}

.vuecal__cell--today,
.vuecal__cell--current {
    background-color: rgb(210, 227, 252);
}

.vuecal__cell--selected:before,
.vuecal:not(.vuecal--day-view) .vuecal__cell--selected {
    background-color: rgba(255, 255, 255, 0);
}

.vuecal__cell--highlighted:not(.vuecal__cell--has-splits),
.vuecal__cell-split--highlighted {
    background-color: rgb(210, 227, 252);
}

.vuecal__arrow.vuecal__arrow--highlighted,
.vuecal__view-btn.vuecal__view-btn--highlighted {
    background-color: var(--color-blue);
}


.vuecal--month-view .vuecal__cell {
    height: 200px;
    width: 150px;
}

.vuecal__time-cell-label {
    font-size: 8px;
}


.vuecal--month-view .vuecal__cell-content {
    justify-content: flex-start;
    height: 100%;
    align-items: flex-end;
}

.vuecal--month-view .vuecal__cell-date {
    padding: 4px;
}

.vuecal--month-view .vuecal__no-event {
    display: none;
}

.vuecal__cell-events-count {
    width: 18px;
    height: 2px;
    color: transparent;
}

.vuecal__cell-events-count {
    width: 4px;
    min-width: 0;
    height: 4px;
    padding: 0;
    color: transparent;
}

.vuecal__cell--has-events {
    background-color: rgba(255, 255, 255, 0);
}

.vuecal__cell-events-count {
    display: none;
}

// Customer

.vuecal__event {
    cursor: pointer;
}

.student {
    background-color: var(--color-blue);
    color: #fff !important;
    border: 1px solid var(--color-white) !important;
}

.important {
    background-color: #B62F35FF;
    color: #fff !important;
    border: 1px solid var(--color-white) !important;

}


.vuecal__event-title {
    font-size: 1.2em;
    font-weight: bold;
    margin: 4px 0 8px;
}

.vuecal__event-time {
    display: inline-block;
    margin-bottom: 12px;
    padding-bottom: 12px;
    border-bottom: 1px solid rgba(0, 0, 0, 0.2);
}

.vuecal__event-content {
    font-style: italic;
}


</style>
