export interface GroupCard {
	groupId: number;
	groupName: string;
	groupDescription: string;
	updateTime?: string;
}
export interface GroupCardList {
	topUpGroupList: GroupCard[];
	defaultGroupList: GroupCard[];
}
