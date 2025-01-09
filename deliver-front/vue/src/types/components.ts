export interface DrawerProps {
	open: boolean;
	title: string;
	placement?: 'left' | 'right' | 'top' | 'bottom' | undefined;
	extra?: boolean;
}
