<template>
  <themecleanflex-components-block v-bind:model="model">
    <div class="flex flex-wrap w-full" v-bind:data-per-path="model.path" v-bind:class="model.alignitems">
      <pagerendervue-components-placeholder v-bind:model="{ path: model.path, component: model.component, location: 'before' }"></pagerendervue-components-placeholder>
      <template v-for="child in model.children">
        <component v-bind:is="child.component" v-bind:model="child" v-bind:key="child.path"></component>
      </template>
      <pagerendervue-components-placeholder v-bind:model="{ path: model.path, component: model.component, location: 'after' }"></pagerendervue-components-placeholder>
    </div>
  </themecleanflex-components-block>
</template>

<script>
    export default {
        props: ['model']
    }
</script>

